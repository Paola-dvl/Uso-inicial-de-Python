package practica1java;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Practica1Java {

    static final int MAX_VEHICULOS = 25;
    static String[][] vehiculos = new String[MAX_VEHICULOS][6];
    static int cantidadVehiculos = 0;
    static final int MAX_CLIENTES = 25;
    static String[][] clientes = new String[MAX_CLIENTES][3];
    static int cantidadClientes = 0;
    static final int MAX_RESERVAS = 25;
    static String[][] reservas = new String[MAX_RESERVAS][4];
    static int cantidadReservas = 0;
    static final int MAX_DESCUENTOS = 25; 
    static int[][] descuentos = new int[MAX_DESCUENTOS][2];
    static int cantidadDescuentos = 0; 
    static boolean[] vehiculoRentado = new boolean[MAX_VEHICULOS];
    private static final int MAX_USUARIOS = 25;
    private static int cantidadUsuariosIniciadosSesion = 0;
    private static final String[][] usuariosIniciadosSesion = new String[MAX_USUARIOS][1];
    private static String nombreCliente;
    private static String nitCliente;
    
    
    public static String obtenerNombreClientePorNIT(String nit) {
    for (int i = 0; i < cantidadClientes; i++) {
        if (clientes[i][0].equals(nit)) {
            return clientes[i][1];
        }
    }
    return null;
}

    public static int obtenerPorcentajeDescuento(int numDias) {
        int porcentajeDescuento = 0;
        for (int i = 0; i < cantidadDescuentos; i++) {
            if (numDias >= descuentos[i][0]) {
                porcentajeDescuento = Math.max(porcentajeDescuento, descuentos[i][1]);
            }
        }
        return porcentajeDescuento;
    }
    
    public static boolean clienteHaIniciadoSesion(String nit) {
    for (int i = 0; i < cantidadUsuariosIniciadosSesion; i++) {
        if (usuariosIniciadosSesion[i][0] != null && usuariosIniciadosSesion[i][0].equals(nit)) {
            return true;
        }
    }
    return false;
}
    
    public static void main(String[] args) {
        menuPrincipal();
    }
    
    public static void menuPrincipal(){
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu Principal:");
            System.out.println("1. Ingresar como Administrador");
            System.out.println("2. Ingresar como Cliente");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> ingresarComoAdministrador();
                case 2 -> ingresarComoCliente();
                case 3 -> isRunning = false;
                default -> System.out.println("Opcion invalida. Por favor, elija una opcion valida.");
            }
        }
        System.out.println("Vuelva Pronto");
    }
    
    public static void ingresarComoAdministrador() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresar como Administrador");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        if (usuario.equals("admin_202200220") && contraseña.equals("202200220")) {
           
            mostrarMenuAdministrador();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    public static void mostrarMenuAdministrador() {
        Scanner scanner = new Scanner(System.in);
        boolean isAdminMenuRunning = true;

        while (isAdminMenuRunning) {
            System.out.println("Menu de Administrador:");
            System.out.println("1. Agregar nuevos vehiculos");
            System.out.println("2. Agregar descuentos");
            System.out.println("3. Editar vehiculo");
            System.out.println("4. Eliminar Vehiculos");
            System.out.println("5. Realizar reportes");
            System.out.println("6. Mostrar usuarios");
            System.out.println("7. Regresar al menu principal");

            char opcion = scanner.next().charAt(0);

            switch (opcion) {
                case '1' -> agregarVehiculo();
                case '2' -> agregarDescuentoEspecial();
                case '3' -> editarVehiculo();
                case '4' -> eliminarVehiculo();
                case '5' -> reporte();
                case '6' -> mostrarUsuarios();
                case '7' -> isAdminMenuRunning = false;
                default -> System.out.println("Opcion invalida. Por favor, elija una opción valida.");
            }
        }
    }

    public static void agregarVehiculo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Agregar nuevos vehiculos al sistema");

        if (cantidadVehiculos < MAX_VEHICULOS) {
            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Linea: ");
            String linea = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            System.out.print("Placa: ");
            String placa = scanner.nextLine();

            if (verificarPlacaExistente(placa)) {
                System.out.println("La placa ingresada ya existe en el sistema.");
                return;
            }
            
            double costoPorDia = 0;
            boolean isCostoValid = false;
            while (!isCostoValid) {
                try {
                    System.out.print("Costo por dia de alquiler (en quetzales): ");
                    costoPorDia = Double.parseDouble(scanner.nextLine());
                    if (costoPorDia <= 0) {
                        System.out.println("El costo por dia de alquiler debe ser mayor a 0.");
                    } else {
                        isCostoValid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, introduzca un numero valido.");
                }
            }
            vehiculos[cantidadVehiculos][0] = marca;
            vehiculos[cantidadVehiculos][1] = linea;
            vehiculos[cantidadVehiculos][2] = modelo;
            vehiculos[cantidadVehiculos][3] = placa;
            vehiculos[cantidadVehiculos][4] = Double.toString(costoPorDia);
            cantidadVehiculos++;
            vehiculoRentado[cantidadVehiculos] = false;

            System.out.println("Vehiculo agregado al sistema con exito.");
        } else {
            System.out.println("El sistema de vehiculos está lleno. No se pueden agregar mas vehiculos.");
        }
    }

    public static boolean verificarPlacaExistente(String placa) {
        for (int i = 0; i < cantidadVehiculos; i++) {
            if (vehiculos[i][3] != null && vehiculos[i][3].equals( placa)) {
                return true;
            }
        }
        return false;
    }

    public static void agregarDescuentoEspecial() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Agregar descuentos especiales");

        System.out.print("Numero de dias minimo de alquiler: ");
        int diasMinimos = scanner.nextInt();

        if (diasMinimos <= 0) {
            System.out.println("El numero de dias minimo debe ser mayor a 0.");
            return;
        }
        double porcentajeDescuento = 0;
        boolean isPorcentajeValid = false;
        while (!isPorcentajeValid) {
            try {
                System.out.print("Porcentaje de descuento: ");
                porcentajeDescuento = Double.parseDouble(scanner.nextLine());
                if (porcentajeDescuento <= 0 || porcentajeDescuento >= 100) {
                    System.out.println("El porcentaje de descuento debe estar entre 1 y 99.");
                } else {
                    isPorcentajeValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un numero valido.");
            }
        }
        descuentos[cantidadDescuentos][0] = diasMinimos;
        descuentos[cantidadDescuentos][1] = (int) porcentajeDescuento;
        cantidadDescuentos++;
        
        System.out.println("Descuento agregado con exito.");
    }

    public static void reporte() {
        System.out.println("Reporte de Marcas con mas dias rentados:");
        System.out.println("Marca\tDias Rentados");

        for (int i = 0; i < cantidadVehiculos; i++) {
            String marca = vehiculos[i][0];
            String diasRentados = vehiculos[i][4];

            System.out.println(marca + "\t" + diasRentados);
        }
    }
            
    public static void mostrarUsuarios() {
        System.out.println("Mostrar Usuarios:");
        System.out.println("NIT\tNombre\tApellido");

        for (int i = 0; i < cantidadClientes; i++) {
            System.out.println(clientes[i][0] + "\t" + clientes[i][1] + "\t" + clientes[i][2]);
        }
    }

    public static void ingresarComoCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu de Cliente");
        
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesion");
        System.out.println("3. Realizar reservas");
        System.out.println("5. Cerrar sesion");

        char opcion = scanner.next().charAt(0);

        switch (opcion) {
            case '1' -> registrarCliente();
            case '2' -> iniciarSesionCliente();
            case '3' -> realizarReservas();
            case '4' -> menuPrincipal();
            default -> System.out.println("Opcion invalida. Por favor, elija una opcion valida.");
            }
    }
    
    public static void registrarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registrarse");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("NIT: ");
        String nit = scanner.nextLine();

        // Verificar si el NIT ya está registrado
        if (verificarNITRegistrado(nit)) {
            System.out.println("El NIT ingresado ya esta registrado en el sistema.");
            return;
            ingresarComoCliente();
        }

        // Agregar el cliente a la matriz de clientes
        clientes[cantidadClientes][0] = nit;
        clientes[cantidadClientes][1] = nombre;
        clientes[cantidadClientes][2] = apellido;
        cantidadClientes++;

        System.out.println("Registrado con exito.");
        ingresarComoCliente();
    }

    public static boolean verificarNITRegistrado(String nit) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i][0] != null && clientes[i][0].equals(nit)) {
                return true;
            }
        }
        return false;
    }

    public static void iniciarSesionCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iniciar sesion");

        System.out.print("Ingrese su NIT: ");
        String nit = scanner.nextLine();

        if (verificarNITRegistrado(nit)) {
            nitCliente = nit;
            nombreCliente = obtenerNombreClientePorNIT(nit); 
            usuariosIniciadosSesion[cantidadUsuariosIniciadosSesion][0] = nit;
            cantidadUsuariosIniciadosSesion++;
            
            System.out.println("Inicio de sesion exitoso.");
          
        } else {
            System.out.println("NIT no registrado en el sistema.");
            nombreCliente = obtenerNombreClientePorNIT(nit);
        }
        ingresarComoCliente();
    }

    public static void realizarReservas() { 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Realizar reservas");
        
        System.out.print("Ingrese su NIT: ");
        String nitCliente = scanner.nextLine();
        if (!clienteHaIniciadoSesion(nitCliente)) {
         System.out.println("Debe iniciar sesion para ingresar.");
         return;
        }
        
        System.out.println("1. Mostrar listado de vehiculos");
        System.out.println("2. Reservar vehiculo");
        System.out.println("3. Vehiculos rentados");
        System.out.println("4. salir");
        
        char opcion = scanner.next().charAt(0);
        
        switch (opcion) {
            case '1' -> mostrarListadoVehiculos();
            case '2' -> reservarVehiculo();
            case '3' -> mostrarVehiculosRentados();
            case '4' -> menuPrincipal();
            default -> System.out.println("Opcion invalida. Por favor, elija una opcion valida.");
        }
    }
    
    public static void mostrarListadoVehiculos() {
         System.out.println("Listado de vehiculos disponibles:");
        System.out.println("Marca\tModelo\tLinea\tPlaca\tCosto total");

        for (int i = 0; i < cantidadVehiculos; i++) {
            if (!vehiculoRentado[i]) {
            System.out.println(vehiculos[i][0] + "\t" + vehiculos[i][2] + "\t" + vehiculos[i][1] + "\t" + vehiculos[i][3] + "\t" + vehiculos[i][4]);
            }
        }
    }
    
    public static void reservarVehiculo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reservar vehiculo");
        mostrarListadoVehiculos();
        System.out.println("\n------------descuentos segun dias de arrendamiento-------------------------");
        System.out.println("dias             porcentaje de descuento %");
        for (int i = 0; i < cantidadDescuentos; i++) {
            System.out.println(descuentos[i][0] + "                    " + descuentos[i][1]);
        }

        System.out.print("\nIngrese la placa del vehiculo que desea rentar: ");
        String placa = scanner.nextLine();

        int indiceVehiculo = obtenerIndiceVehiculo(placa);
        if (indiceVehiculo == -1) {
            System.out.println("El identificador ingresado no existe.");
        } else {
            System.out.print("Numero de dias a rentar: ");
            int numDias = scanner.nextInt();
            vehiculos[indiceVehiculo][5] = Integer.toString(numDias);

            int indiceVehiculoReservado = obtenerIndiceVehiculo(placa);
            vehiculoRentado[indiceVehiculoReservado] = true;
            System.out.println("Vehiculo rentado con exito.");
            double costoPorDia = Double.parseDouble(vehiculos[indiceVehiculo][4]);
            double costoTotal = numDias * costoPorDia;

            int porcentajeDescuento = obtenerPorcentajeDescuento(numDias);
            double descuento = costoTotal * (porcentajeDescuento / 100.0);

            double subtotal = costoTotal - descuento;
            double total = subtotal;
        
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fechaHoraEmision = new Date();
        
            System.out.println("\n------------------------ Factura ------------------------");
            System.out.println("Empresa: ByteCar");
            System.out.println("Cliente: " + nombreCliente);
            System.out.println("NIT: " + nitCliente);
            System.out.println("Fecha y hora de emision: " + sdf.format(fechaHoraEmision));
            System.out.println("Listado de vehiculos rentados:");
            System.out.println("Modelo\tLinea\tPlaca\tDias rentados\tCosto total");
            System.out.println(vehiculos[indiceVehiculo][2] + "\t" + vehiculos[indiceVehiculo][1] + "\t" +
                               vehiculos[indiceVehiculo][3] + "\t" + numDias + "\t" + costoTotal);
            System.out.println("Subtotal: " + subtotal);
            System.out.println("Porcentaje de descuento: " + porcentajeDescuento + "%");
            double totalConDescuento = total - (total * (porcentajeDescuento / 100.0));
            System.out.println("Total (con descuento): " + totalConDescuento);
            System.out.println("------------------------------------------------------");
          
        }
    }

    public static int obtenerIndiceVehiculo(String identificador) {
        for (int i = 0; i < cantidadVehiculos; i++) {
            if (vehiculos[i][3] != null && vehiculos[i][3].equals(identificador)) {
                return i;
            }
        }
        return 1;
    }
    
    public static void mostrarVehiculosRentados() {
        System.out.println("Listado de vehiculos rentados:");
        System.out.println("Marca\tModelo\tLinea\tPlaca\tCosto por dia");

        for (int i = 0; i < cantidadVehiculos; i++) {
            if (vehiculoRentado[i]) {
                
                System.out.println(vehiculos[i][0] + "\t" + vehiculos[i][2] + "\t" + vehiculos[i][1] + "\t" + vehiculos[i][3] + "\t" + vehiculos[i][4]);
            }
        }
    }
    
    public static void editarVehiculo() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Editar vehiculo");
    mostrarListadoVehiculos();

    System.out.print("Ingrese la placa del vehiculo que desea editar: ");
    String placa = scanner.nextLine();

    int indiceVehiculo = obtenerIndiceVehiculo(placa);
    if (indiceVehiculo == -1) {
    System.out.println("El vehiculo con la placa ingresada no existe.");
    } else {
        
        System.out.println("Ingrese los nuevos datos del vehiculo:");

        System.out.print("Marca: ");
        vehiculos[indiceVehiculo][0] = scanner.nextLine();

        System.out.print("Linea: ");
        vehiculos[indiceVehiculo][1] = scanner.nextLine();

        System.out.print("Modelo: ");
        vehiculos[indiceVehiculo][2] = scanner.nextLine();

        double costoPorDia = 0;
        boolean isCostoValid = false;
        while (!isCostoValid) {
            try {
                System.out.print("Costo por dia de alquiler (en quetzales): ");
                costoPorDia = Double.parseDouble(scanner.nextLine());
                if (costoPorDia <= 0) {
                    System.out.println("El costo por dia de alquiler debe ser mayor a 0.");
                } else {
                    isCostoValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un numero valido.");
            }
        }
        vehiculos[indiceVehiculo][4] = Double.toString(costoPorDia);

        System.out.println("Vehiculo editado con exito.");
        }
    }   
    
    public static void eliminarVehiculo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Eliminar vehiculo");
        mostrarListadoVehiculos();

        System.out.print("Ingrese la placa del vehiculo que desea eliminar: ");
        String placa = scanner.nextLine();

        int indiceVehiculo = obtenerIndiceVehiculo(placa);

        if (indiceVehiculo == -1) {
            System.out.println("El vehiculo con la placa ingresada no existe.");
            return;
        }

        if (vehiculoRentado[indiceVehiculo]) {
            System.out.println("No es posible eliminar un vehiculo rentado.");
            return;
        }

        for (int i = indiceVehiculo; i < cantidadVehiculos - 1; i++) {
            for (int j = 0; j < 5; j++) {
                vehiculos[i][j] = vehiculos[i + 1][j];
            }
        }
        cantidadVehiculos--;

        System.out.println("Vehiculo eliminado con exito.");
        }

}
