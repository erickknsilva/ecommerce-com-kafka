package com.br.erickWck.ecommerce;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
    private final KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        orderDispatcher.close();
        emailDispatcher.close();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            String orderId = UUID.randomUUID().toString();

            var ammount = new BigDecimal(req.getParameter("ammount"));
            var email = req.getParameter("email");
            var order = new Order(orderId, ammount, email);

            String body = "Obrigado por realizar sua compra.";
            var emailteste = new Email(email, body);
            orderDispatcher.send("ECOMMERCE_NEW_ORDER", orderId, order);
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailteste);
            System.out.println("New order sent successfuly");

            resp.getWriter().println("New order send");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ExecutionException e) {
            throw new ServletException(e);
        } catch (InterruptedException e) {
            throw new ServletException(e);
        }


    }
}

