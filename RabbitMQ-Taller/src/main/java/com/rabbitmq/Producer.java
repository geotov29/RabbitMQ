package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;
import java.util.UUID;

//Sistema de envio de facturas.

public class Producer {

    private final static String QUEUE_NAME = "FacturasGeneradas";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            Scanner scanner = new Scanner(System.in);
            String continueSending = "y";

            while (continueSending.equalsIgnoreCase("y")) {
                System.out.println("Digite el nombre del Cliente:");
                String nombre = scanner.nextLine();

                System.out.println("Digite el documento:");
                String documento = Integer.parseInt(scanner.nextLine());

                System.out.println("Digite el valor de la factura:");
                String valor = Double.parseDouble(scanner.nextLine());

                System.out.println("Digite el numero de la factura:");
                String   numerofactura = Double.parseDouble(scanner.nextLine());
                
                String Id = UUID.randomUUID().toString();
                String orden = "ID: " + Id + ", nombre: " + nombre + ", documento: " + documento + ", ValorFactura: $" + valor + ", NumeroFactura: " + numerofactura;

                channel.basicPublish("", QUEUE_NAME, null, orden.getBytes());
                System.out.println(" [x] Enviado: '" + orden + "'");

                System.out.println("¿Desea enviar otra factura? (y/n):");
                continueSending = scanner.nextLine();
            }
        }
    }
}

