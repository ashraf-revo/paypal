package org.revo

import com.paypal.api.payments.Amount
import com.paypal.api.payments.Details
import com.paypal.api.payments.Item
import com.paypal.api.payments.ItemList
import com.paypal.api.payments.Payer
import com.paypal.api.payments.Payment
import com.paypal.api.payments.RedirectUrls
import com.paypal.api.payments.Transaction
import com.paypal.base.rest.APIContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletRequest

@SpringBootApplication
class PaypalApplication {
    static void main(String[] args) {
        SpringApplication.run PaypalApplication, args
    }

    @Bean
    CommandLineRunner runner() {
        { args ->
        }
    }
}

@Controller
class con {
    @Autowired
    APIContext apiContext

    @RequestMapping
    def index(HttpServletRequest request) {
        Details details = new Details();
        details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("7");
        amount.setDetails(details);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment transaction description.");
        Item item = new Item();
        item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        String ss = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.contextPath
        redirectUrls.setCancelUrl(ss + "?guid=" + guid);
        redirectUrls.setReturnUrl(ss + "?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);
        Payment create = payment.create(apiContext);
        return create.redirectUrls
    }

}
