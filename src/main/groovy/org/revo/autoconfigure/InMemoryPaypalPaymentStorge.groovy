package org.revo.autoconfigure

import com.paypal.api.payments.Payment

import java.util.concurrent.ConcurrentHashMap

/**
 * Created by ashraf on 2/15/2016.
 */
class InMemoryPaypalPaymentStorge implements PaypalPaymentStorge {
    Map<String, Payment> map = new ConcurrentHashMap<>()

    @Override
    Payment findOne(String paymentId) {
        if (exist(paymentId)) map.get(paymentId)
        else throw new PaymentException("cant find payment with payment id = " + paymentId)
    }

    void save(String paymentId, Payment payment) {
        if (!exist(paymentId)) map.put(paymentId, payment)
        else throw new PaymentException("cant duplicate payment with same payment id = " + paymentId)
    }

    boolean exist(String paymentId) {
        map.containsKey(paymentId)
    }

    boolean isNotChanged(String paymentId, Payment payment) {
        Payment one = findOne(paymentId)
        Double OneSum = 0
        one.transactions.each {
            OneSum += it.amount.total.toDouble()
        }
        Double PaymentSum = 0
        payment.transactions.each {
            PaymentSum += it.amount.total.toDouble()
        }
        PaymentSum == OneSum
    }

    void delete(String paymentId) {
        map.remove(paymentId)
    }
}

