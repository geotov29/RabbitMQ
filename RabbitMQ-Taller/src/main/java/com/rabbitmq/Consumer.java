package com.rabbitmq;

import com.rabbitmq.client.*;

public class Consumer {

    private final static String QUEUE_NAME = "ordenesProductos";

    public static void main(String[] argv) throws Exception 
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Esperando mensajes. presione CTRL+C para salir");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recibido '" + message + "'");
            
            ObtenerDatos(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

    private static void ObtenerDatos(String order) 
    {
        
        String nombre = extractValue(order, "Nombre cliente: ");
        String documento = extractValue(order, "Documento: ");
        String numerofactura = extractValue(order, "Documento: ");
        String ValorFactura = extractValue(order, "Documento: ");

        try
        {
            System.out.println("Factura recibida:" + numerofactura);
        } 
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private static String extractValue(String order, String key) 
    {
        
        int start = order.indexOf(key) + key.length();
        int end = order.indexOf(",", start);
        if (end == -1) 
        {
            end = order.length();
        }
        
        return order.substring(start, end).trim();
    }
}
