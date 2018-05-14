package com.company.salescafe.service;

import com.company.salescafe.entity.Order;
import com.company.salescafe.entity.OrderCard;
import com.company.salescafe.entity.OrderStatus;
import com.company.salescafe.entity.Product;
import com.company.salescafe.services.OrderService;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.TimeSource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.TemporalType;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(OrderService.NAME)
public class OrderServiceBean implements OrderService {

    @Inject
    private Persistence persistence;
    @Inject
    private TimeSource timeSource;

    @Override
    public int generateNewOrderNumber(UUID workDayId) {
        final Transaction tx = persistence.getTransaction();
        try {
            final EntityManager em = persistence.getEntityManager();
            TypedQuery<Order> typedQuery = em.createQuery("select t from salescafe$Order t where t.workDay.id = ?1", Order.class)
                    .setParameter(1, workDayId);
            List<Order> orderList = typedQuery.getResultList();
            Order order = orderList.stream().max(Comparator.comparing(Order::getNumberOfOrder)).orElse(null);
            return order == null ? 1 : order.getNumberOfOrder() + 1;
        } finally {
            tx.end();
        }
    }

    @Override
    public void setOrderStatus(Order order, OrderStatus status) {
        final Transaction tx = persistence.getTransaction();
        try {
            final EntityManager em = persistence.getEntityManager();
            order.setOrderStatus(status);
            em.merge(order);
            tx.commit();
        } finally {
            tx.end();
        }
    }

    @Override
    public List<OrderCard> getFilteredBetweenDateList(Date startDate, Date endDate) {
        final Transaction tx = persistence.getTransaction();
        try {
            final EntityManager em = persistence.getEntityManager();
            TypedQuery<OrderCard> orderCardQUERY = em.createQuery("select e from salescafe$OrderCard e where e.order.timeOfOrder " +
                    "BETWEEN :startDate and :endDate order by e.order.timeOfOrder", OrderCard.class)
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE);
            orderCardQUERY.setView(OrderCard.class, "orderCard-view");
            return orderCardQUERY.getResultList();
        } finally {
            tx.end();
        }
    }

    @Override
    public void changeResidueAfterPurchase(Product product) {
        final Transaction tx = persistence.getTransaction();
        try {
            final EntityManager em = persistence.getEntityManager();
            if (product.getResidue() != null && product.getResidue() > 0)
                product.setResidue(product.getResidue() - 1);
            em.merge(product);
            tx.commit();
        } finally {
            tx.end();
        }
    }
}
