package org.example.proxy4.service_client;

import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerService
{
    public static void main(String[]args) throws RemoteException
    {
        //--- Gestion des numéros de ports en paramètres du programme ---
        int portService = 0;

        //--- Gestion de la connexion RMI ---
        try {
            //On crée une instance du service
            Service service = new Service();

            //On met le service à disposition via un port de la machine
            ServiceInterface serviceInterface = (ServiceInterface) UnicastRemoteObject.exportObject(service, portService);

            // Getting central service
            Registry reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            ServiceDistributeur dist = (ServiceDistributeur) reg.lookup("distributeur");

            // Register to the Central service
            dist.addService(serviceInterface);

            System.out.println("Service Started");
        }
        catch (java.rmi.server.ExportException e) {
            System.err.println("Port " + portService + " déjà utilisé : impossible d'y affecter le service");
            System.exit(-1);
        }
        catch (RemoteException r){
            System.out.println("Failed to add calcule to serveer");
            r.printStackTrace();
        }
         catch (NotBoundException e) {
            System.out.println("Failed to get central Service named 'Distibuteur' ");
        }
    }
}