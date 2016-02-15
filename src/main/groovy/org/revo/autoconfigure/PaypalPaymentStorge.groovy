package org.revo.autoconfigure

import com.paypal.api.payments.Payment

/**
 * Created by ashraf on 2/15/2016.
 */
interface PaypalPaymentStorge {
    Payment findOne(String paymentId)

    void save(String paymentId, Payment payment)

    boolean isNotChanged(String paymentId, Payment payment)

    void delete(String paymentId)
}