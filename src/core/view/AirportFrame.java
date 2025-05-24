/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package core.view;

import core.models.Flight;
import core.models.Location;
import core.models.Passenger;
import core.models.Plane;
import com.formdev.flatlaf.FlatDarkLaf;
import core.controllers.FlightController;
import core.controllers.LocationController;
import core.controllers.PassengerController;
import core.controllers.PlaneController;
import core.controllers.tableLists.FlightTableList;
import core.controllers.tableLists.LocationTableList;
import core.controllers.tableLists.PassengerFlightsTableList;
import core.controllers.tableLists.PassengerTableList;
import core.controllers.tableLists.PlaneTableList;
import core.controllers.tableLists.TableRefresherObserver;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.data.ComboLoader;
import core.data.JsonDataLoader;
import core.models.utils.FlightDelay;
import core.models.utils.FlightCalculations;
import core.models.storage.AirportStorage;
import core.models.storage.FlightRepository;
import core.models.storage.LocationRepository;
import core.models.storage.PassengerRepository;
import core.models.storage.PlaneRepository;
import core.models.utils.PassengerCalculations;
import core.models.utils.PassengerFormatter;
import core.models.utils.PlaneCalculations;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edangulo
 */
public class AirportFrame extends javax.swing.JFrame {

    /**
     * Creates new form AirportFrame
     */
    private int x, y;
    private ArrayList<Passenger> passengers;
    private ArrayList<Plane> planes;
    private ArrayList<Location> locations;
    private ArrayList<Flight> flights;
    private LocationController locationController;
    private FlightRepository flightRepo;
    private LocationRepository locationRepo;
    private PlaneRepository planeRepo;
    private PassengerRepository passengerRepo ;


    FlightCalculations calculator = new FlightCalculations();
    PlaneCalculations planeCalculator = new PlaneCalculations();
    JsonDataLoader loader = new JsonDataLoader();
  

    FlightDelay delayFlight = new FlightDelay();

    public AirportFrame() {
        initComponents();
        // ✅ Inicializar repos
        this.flightRepo = AirportStorage.getInstance().getFlightRepository();
        this.planeRepo = AirportStorage.getInstance().getPlaneRepo();
        this.locationRepo = AirportStorage.getInstance().getLocationRepository();
        this.passengerRepo = AirportStorage.getInstance().getPassengerRepo();
       
        this.passengers = new ArrayList<>();
        this.planes = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();

        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);

        this.generateMonths();
        this.generateDays();
        this.generateHours();
        this.generateMinutes();
        this.blockPanels();
        loader.loadAllData();
        ComboLoader.cargarPasajeros(userSelect);
        ComboLoader.cargarAviones(planeSelect);
        ComboLoader.cargarFlight(idFlight);
        ComboLoader.cargarComboLocation(DepartureLocationSelect);
        ComboLoader.cargarComboLocation(ArrivalLocationSelect);
        ComboLoader.cargarComboLocation(ScaleComboBox);
        ComboLoader.cargarFlight(idFlightPassenger);
        ComboLoader.cargarComboLocation(ScaleComboBox);
        ScaleComboBox.addItem("None");
        TableRefresherObserver observerPassenger = new TableRefresherObserver(() -> {
        PassengerTableList.updatePassengersList((DefaultTableModel) passengerTable.getModel());
        });
        passengerRepo.addObserver(observerPassenger);
        TableRefresherObserver observerPlane = new TableRefresherObserver(() -> {
        PlaneTableList.updatePlanesList((DefaultTableModel) planeTable.getModel());
        });
        planeRepo.addObserver(observerPlane);
        TableRefresherObserver observerLocation = new TableRefresherObserver(() -> {
        LocationTableList.updateLocationsList((DefaultTableModel) locationTable.getModel());
        });
        locationRepo.addObserver(observerLocation);
        TableRefresherObserver observerFlight = new TableRefresherObserver(() -> {
        FlightTableList.updateFlightsList((DefaultTableModel) flightTable.getModel());
        });
        flightRepo.addObserver(observerFlight);
        TableRefresherObserver observerShowMyFlight = new TableRefresherObserver(() -> {
        PassengerFlightsTableList.updatePassengerFlightsList((DefaultTableModel) showMyFlightsTable.getModel(), idPassengerFlightText.getText());
        });
        flightRepo.addObserver(observerShowMyFlight);
    }
    
    private void blockPanels() {
        //9, 11
        for (int i = 1; i < AirportTabbed.getTabCount(); i++) {
            if (i != 9 && i != 11) {
                AirportTabbed.setEnabledAt(i, false);
            }
        }
    }

    private void generateMonths() {
        for (int i = 1; i < 13; i++) {
            MONTH.addItem("" + i);
            MONTH1.addItem("" + i);
            MONTHUpdate.addItem("" + i);
        }
    }

    private void generateDays() {
        for (int i = 1; i < 32; i++) {
            DAY.addItem("" + i);
            DAY1.addItem("" + i);
            DAYupdate.addItem("" + i);
        }
    }

    private void generateHours() {
        for (int i = 0; i < 24; i++) {
            MONTH2.addItem("" + i);
            MONTH3.addItem("" + i);
            HourScale.addItem("" + i);
            hoursDelay.addItem("" + i);
        }
    }

    private void generateMinutes() {
        for (int i = 0; i < 60; i++) {
            DAY2.addItem("" + i);
            DAY3.addItem("" + i);
            MinuteScale.addItem("" + i);
            minutesDelay.addItem("" + i);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new core.view.PanelRound();
        panelRound2 = new core.view.PanelRound();
        jButton13 = new javax.swing.JButton();
        AirportTabbed = new javax.swing.JTabbedPane();
        AdministrationPanel = new javax.swing.JPanel();
        user = new javax.swing.JRadioButton();
        administrator = new javax.swing.JRadioButton();
        userSelect = new javax.swing.JComboBox<>();
        PassengerRegistration = new javax.swing.JPanel();
        countryLabel = new javax.swing.JLabel();
        idPasseneerRegisterLabel = new javax.swing.JLabel();
        FirstNameLabel = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JLabel();
        BirthdateLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        PhoneCodeRegistrationTextField = new javax.swing.JTextField();
        IdRegistrationTextField = new javax.swing.JTextField();
        YearRegistrationTextField = new javax.swing.JTextField();
        countryRegistrationTextField = new javax.swing.JTextField();
        PhoneRegistrationTextField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        LastNameRegistrationTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        MONTH = new javax.swing.JComboBox<>();
        FirstNameRegistrationTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        DAY = new javax.swing.JComboBox<>();
        RegisterPassengerButtom = new javax.swing.JButton();
        PlaneRegistrationPanel = new javax.swing.JPanel();
        idPlaneLabel = new javax.swing.JLabel();
        IdPlaneTextField = new javax.swing.JTextField();
        BrandLabel = new javax.swing.JLabel();
        BrandPlaneTextField = new javax.swing.JTextField();
        ModelPlaneTextField = new javax.swing.JTextField();
        ModelLabel = new javax.swing.JLabel();
        PlaneCapacityTextField11 = new javax.swing.JTextField();
        CapacityLabel = new javax.swing.JLabel();
        PlaneAirlinejTextField = new javax.swing.JTextField();
        AirlineLabel = new javax.swing.JLabel();
        createPlanejButton = new javax.swing.JButton();
        LocationRegistrationPanel = new javax.swing.JPanel();
        AirportIdLabel = new javax.swing.JLabel();
        airportId = new javax.swing.JTextField();
        AirportNameLabel = new javax.swing.JLabel();
        nameAirport = new javax.swing.JTextField();
        cityAirport = new javax.swing.JTextField();
        AirportCityLabel = new javax.swing.JLabel();
        AirportCountryLabel = new javax.swing.JLabel();
        countryAirport = new javax.swing.JTextField();
        latitudeAirport = new javax.swing.JTextField();
        AirportLatitudeLabel = new javax.swing.JLabel();
        AirportLongitudeLabel = new javax.swing.JLabel();
        longitudeAirport = new javax.swing.JTextField();
        createLocationButtom = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        idFlightLabel = new javax.swing.JLabel();
        idFlightTextField = new javax.swing.JTextField();
        planeFlightLabel = new javax.swing.JLabel();
        planeSelect = new javax.swing.JComboBox<>();
        DepartureLocationSelect = new javax.swing.JComboBox<>();
        DepartureLocationLabel = new javax.swing.JLabel();
        ArrivalLocationSelect = new javax.swing.JComboBox<>();
        ArrivalLocationLabel = new javax.swing.JLabel();
        ScaleLocationLabel = new javax.swing.JLabel();
        ScaleComboBox = new javax.swing.JComboBox<>();
        DurantionScaleLabel = new javax.swing.JLabel();
        DurationArrivalLabel = new javax.swing.JLabel();
        DepartureDateLabel = new javax.swing.JLabel();
        añoTextField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        MONTH1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        DAY1 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        MONTH2 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        DAY2 = new javax.swing.JComboBox<>();
        MONTH3 = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        DAY3 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        HourScale = new javax.swing.JComboBox<>();
        MinuteScale = new javax.swing.JComboBox<>();
        FlightCreateButtom = new javax.swing.JButton();
        UpdateInfoPanel = new javax.swing.JPanel();
        IdUpdateUserLabel = new javax.swing.JLabel();
        IdUpdateTextField = new javax.swing.JTextField();
        FirstNameUpdateUserLabel = new javax.swing.JLabel();
        FirstNameUpdateTextField = new javax.swing.JTextField();
        LastNameUpdateLabel = new javax.swing.JLabel();
        LastNameUpdateTextField = new javax.swing.JTextField();
        BirthdateUpdateLabel = new javax.swing.JLabel();
        YearUpdateTextField = new javax.swing.JTextField();
        MONTHUpdate = new javax.swing.JComboBox<>();
        DAYupdate = new javax.swing.JComboBox<>();
        PhoneUpdateTextField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        PhoneCodeUpdateTextField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        PhoneUpdateLabel = new javax.swing.JLabel();
        CountryUpdateLabel = new javax.swing.JLabel();
        CountryUpdateTextField = new javax.swing.JTextField();
        UpdatePassengerButton = new javax.swing.JButton();
        AddToFlightPanel = new javax.swing.JPanel();
        idPassengerFlightText = new javax.swing.JTextField();
        IdAddUserFlightLabel = new javax.swing.JLabel();
        idFlightUserAddLabel = new javax.swing.JLabel();
        idFlightPassenger = new javax.swing.JComboBox<>();
        addPassengerFlightButton = new javax.swing.JButton();
        ShowMyFlightsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        showMyFlightsTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        ShowAllPassengersPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        passengerTable = new javax.swing.JTable();
        RefreshPassengerTableButtom = new javax.swing.JButton();
        ShowAllFlightsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        flightTable = new javax.swing.JTable();
        RefreshFlightsButtom = new javax.swing.JButton();
        ShowAllPlanesPanel = new javax.swing.JPanel();
        RefreshPlaneButtom = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        planeTable = new javax.swing.JTable();
        ShowAllLocationsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        locationTable = new javax.swing.JTable();
        RefreshLocationButtom = new javax.swing.JButton();
        DelayFlightPanel = new javax.swing.JPanel();
        hoursDelay = new javax.swing.JComboBox<>();
        HourLabel = new javax.swing.JLabel();
        IdFlightLabel = new javax.swing.JLabel();
        idFlight = new javax.swing.JComboBox<>();
        MinuteLabel = new javax.swing.JLabel();
        minutesDelay = new javax.swing.JComboBox<>();
        DelayButtom = new javax.swing.JButton();
        panelRound3 = new core.view.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(40);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jButton13.setText("X");
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(1083, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addComponent(jButton13)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelRound1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        AirportTabbed.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        AdministrationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        user.setText("User");
        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });
        AdministrationPanel.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, -1, -1));

        administrator.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        administrator.setText("Administrator");
        administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorActionPerformed(evt);
            }
        });
        AdministrationPanel.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 164, -1, -1));

        userSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User" }));
        userSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSelectActionPerformed(evt);
            }
        });
        AdministrationPanel.add(userSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, -1));

        AirportTabbed.addTab("Administration", AdministrationPanel);

        PassengerRegistration.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        countryLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        countryLabel.setText("Country:");
        PassengerRegistration.add(countryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        idPasseneerRegisterLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idPasseneerRegisterLabel.setText("ID:");
        PassengerRegistration.add(idPasseneerRegisterLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        FirstNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FirstNameLabel.setText("First Name:");
        PassengerRegistration.add(FirstNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        LastNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        LastNameLabel.setText("Last Name:");
        PassengerRegistration.add(LastNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        BirthdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        BirthdateLabel.setText("Birthdate:");
        PassengerRegistration.add(BirthdateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel6.setText("+");
        PassengerRegistration.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 20, -1));

        PhoneCodeRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PassengerRegistration.add(PhoneCodeRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 50, -1));

        IdRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IdRegistrationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdRegistrationTextFieldActionPerformed(evt);
            }
        });
        PassengerRegistration.add(IdRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, -1));

        YearRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        YearRegistrationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearRegistrationTextFieldActionPerformed(evt);
            }
        });
        PassengerRegistration.add(YearRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        countryRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PassengerRegistration.add(countryRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, -1));

        PhoneRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PassengerRegistration.add(PhoneRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 130, -1));

        phoneLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        phoneLabel.setText("Phone:");
        PassengerRegistration.add(phoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("-");
        PassengerRegistration.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, -1));

        LastNameRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PassengerRegistration.add(LastNameRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("-");
        PassengerRegistration.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, -1));

        MONTH.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        PassengerRegistration.add(MONTH, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        FirstNameRegistrationTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FirstNameRegistrationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstNameRegistrationTextFieldActionPerformed(evt);
            }
        });
        PassengerRegistration.add(FirstNameRegistrationTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("-");
        PassengerRegistration.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 30, -1));

        DAY.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));
        PassengerRegistration.add(DAY, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        RegisterPassengerButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RegisterPassengerButtom.setText("Register");
        RegisterPassengerButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterPassengerButtomActionPerformed(evt);
            }
        });
        PassengerRegistration.add(RegisterPassengerButtom, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        AirportTabbed.addTab("Passenger registration", PassengerRegistration);

        PlaneRegistrationPanel.setLayout(null);

        idPlaneLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idPlaneLabel.setText("ID:");
        PlaneRegistrationPanel.add(idPlaneLabel);
        idPlaneLabel.setBounds(53, 96, 22, 25);

        IdPlaneTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneRegistrationPanel.add(IdPlaneTextField);
        IdPlaneTextField.setBounds(180, 93, 130, 31);

        BrandLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        BrandLabel.setText("Brand:");
        PlaneRegistrationPanel.add(BrandLabel);
        BrandLabel.setBounds(53, 157, 52, 25);

        BrandPlaneTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneRegistrationPanel.add(BrandPlaneTextField);
        BrandPlaneTextField.setBounds(180, 154, 130, 31);

        ModelPlaneTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneRegistrationPanel.add(ModelPlaneTextField);
        ModelPlaneTextField.setBounds(180, 213, 130, 31);

        ModelLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ModelLabel.setText("Model:");
        PlaneRegistrationPanel.add(ModelLabel);
        ModelLabel.setBounds(53, 216, 57, 25);

        PlaneCapacityTextField11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneRegistrationPanel.add(PlaneCapacityTextField11);
        PlaneCapacityTextField11.setBounds(180, 273, 130, 31);

        CapacityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        CapacityLabel.setText("Max Capacity:");
        PlaneRegistrationPanel.add(CapacityLabel);
        CapacityLabel.setBounds(53, 276, 114, 25);

        PlaneAirlinejTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneRegistrationPanel.add(PlaneAirlinejTextField);
        PlaneAirlinejTextField.setBounds(180, 333, 130, 31);

        AirlineLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirlineLabel.setText("Airline:");
        PlaneRegistrationPanel.add(AirlineLabel);
        AirlineLabel.setBounds(53, 336, 70, 25);

        createPlanejButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createPlanejButton.setText("Create");
        createPlanejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPlanejButtonActionPerformed(evt);
            }
        });
        PlaneRegistrationPanel.add(createPlanejButton);
        createPlanejButton.setBounds(490, 480, 120, 40);

        AirportTabbed.addTab("Airplane registration", PlaneRegistrationPanel);

        AirportIdLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportIdLabel.setText("Airport ID:");

        airportId.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        AirportNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportNameLabel.setText("Airport name:");

        nameAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        cityAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        AirportCityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportCityLabel.setText("Airport city:");

        AirportCountryLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportCountryLabel.setText("Airport country:");

        countryAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        latitudeAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        AirportLatitudeLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportLatitudeLabel.setText("Airport latitude:");

        AirportLongitudeLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AirportLongitudeLabel.setText("Airport longitude:");

        longitudeAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        createLocationButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createLocationButtom.setText("Create");
        createLocationButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLocationButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LocationRegistrationPanelLayout = new javax.swing.GroupLayout(LocationRegistrationPanel);
        LocationRegistrationPanel.setLayout(LocationRegistrationPanelLayout);
        LocationRegistrationPanelLayout.setHorizontalGroup(
            LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                .addGroup(LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AirportIdLabel)
                            .addComponent(AirportNameLabel)
                            .addComponent(AirportCityLabel)
                            .addComponent(AirportCountryLabel)
                            .addComponent(AirportLatitudeLabel)
                            .addComponent(AirportLongitudeLabel))
                        .addGap(80, 80, 80)
                        .addGroup(LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(longitudeAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(airportId, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cityAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(countryAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(latitudeAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(createLocationButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(515, 515, 515))
        );
        LocationRegistrationPanelLayout.setVerticalGroup(
            LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(AirportIdLabel)
                        .addGap(36, 36, 36)
                        .addComponent(AirportNameLabel)
                        .addGap(34, 34, 34)
                        .addComponent(AirportCityLabel)
                        .addGap(35, 35, 35)
                        .addComponent(AirportCountryLabel)
                        .addGap(35, 35, 35)
                        .addComponent(AirportLatitudeLabel))
                    .addGroup(LocationRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(airportId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(nameAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cityAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(countryAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(latitudeAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(LocationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AirportLongitudeLabel)
                    .addComponent(longitudeAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(createLocationButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        AirportTabbed.addTab("Location registration", LocationRegistrationPanel);

        idFlightLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idFlightLabel.setText("ID:");

        idFlightTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        planeFlightLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        planeFlightLabel.setText("Plane:");

        planeSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        planeSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plane" }));
        planeSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planeSelectActionPerformed(evt);
            }
        });

        DepartureLocationSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureLocationSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));
        DepartureLocationSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartureLocationSelectActionPerformed(evt);
            }
        });

        DepartureLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureLocationLabel.setText("Departure location:");

        ArrivalLocationSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ArrivalLocationSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));
        ArrivalLocationSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrivalLocationSelectActionPerformed(evt);
            }
        });

        ArrivalLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ArrivalLocationLabel.setText("Arrival location:");

        ScaleLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ScaleLocationLabel.setText("Scale location:");

        ScaleComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ScaleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        DurantionScaleLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DurantionScaleLabel.setText("Duration:");

        DurationArrivalLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DurationArrivalLabel.setText("Duration:");

        DepartureDateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureDateLabel.setText("Departure date:");

        añoTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setText("-");

        MONTH1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setText("-");

        DAY1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("-");

        MONTH2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("-");

        DAY2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        MONTH3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("-");

        DAY3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("-");

        HourScale.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        HourScale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        MinuteScale.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MinuteScale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        FlightCreateButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FlightCreateButtom.setText("Create");
        FlightCreateButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightCreateButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(ScaleLocationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ScaleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ArrivalLocationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ArrivalLocationSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(DepartureLocationLabel)
                        .addGap(46, 46, 46)
                        .addComponent(DepartureLocationSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idFlightLabel)
                            .addComponent(planeFlightLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idFlightTextField)
                            .addComponent(planeSelect, 0, 130, Short.MAX_VALUE))))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DurantionScaleLabel)
                    .addComponent(DurationArrivalLabel)
                    .addComponent(DepartureDateLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(añoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(MONTH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DAY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(MONTH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DAY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(MONTH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(DAY3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(HourScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(MinuteScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(FlightCreateButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(530, 530, 530))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(idFlightLabel))
                    .addComponent(idFlightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(planeFlightLabel)
                    .addComponent(planeSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MONTH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(DAY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(DepartureLocationLabel)
                                .addComponent(DepartureLocationSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DepartureDateLabel))
                            .addComponent(añoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MONTH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(DAY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ArrivalLocationLabel)
                                .addComponent(ArrivalLocationSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DurationArrivalLabel))
                            .addComponent(MONTH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(DAY3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HourScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(MinuteScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ScaleLocationLabel)
                                .addComponent(ScaleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DurantionScaleLabel)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(FlightCreateButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        AirportTabbed.addTab("Flight registration", jPanel4);

        IdUpdateUserLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IdUpdateUserLabel.setText("ID:");

        IdUpdateTextField.setEditable(false);
        IdUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IdUpdateTextField.setEnabled(false);
        IdUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdUpdateTextFieldActionPerformed(evt);
            }
        });

        FirstNameUpdateUserLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FirstNameUpdateUserLabel.setText("First Name:");

        FirstNameUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        LastNameUpdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        LastNameUpdateLabel.setText("Last Name:");

        LastNameUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        BirthdateUpdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        BirthdateUpdateLabel.setText("Birthdate:");

        YearUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        YearUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearUpdateTextFieldActionPerformed(evt);
            }
        });

        MONTHUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTHUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        DAYupdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAYupdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        PhoneUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setText("-");

        PhoneCodeUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setText("+");

        PhoneUpdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PhoneUpdateLabel.setText("Phone:");

        CountryUpdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        CountryUpdateLabel.setText("Country:");

        CountryUpdateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        UpdatePassengerButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        UpdatePassengerButton.setText("Update");
        UpdatePassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdatePassengerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UpdateInfoPanelLayout = new javax.swing.GroupLayout(UpdateInfoPanel);
        UpdateInfoPanel.setLayout(UpdateInfoPanelLayout);
        UpdateInfoPanelLayout.setHorizontalGroup(
            UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(IdUpdateUserLabel)
                                .addGap(108, 108, 108)
                                .addComponent(IdUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(FirstNameUpdateUserLabel)
                                .addGap(41, 41, 41)
                                .addComponent(FirstNameUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(LastNameUpdateLabel)
                                .addGap(43, 43, 43)
                                .addComponent(LastNameUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(BirthdateUpdateLabel)
                                .addGap(55, 55, 55)
                                .addComponent(YearUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(MONTHUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(DAYupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(PhoneUpdateLabel)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(PhoneCodeUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(PhoneUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                                .addComponent(CountryUpdateLabel)
                                .addGap(63, 63, 63)
                                .addComponent(CountryUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(UpdatePassengerButton)))
                .addContainerGap(598, Short.MAX_VALUE))
        );
        UpdateInfoPanelLayout.setVerticalGroup(
            UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateInfoPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IdUpdateUserLabel)
                    .addComponent(IdUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FirstNameUpdateUserLabel)
                    .addComponent(FirstNameUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LastNameUpdateLabel)
                    .addComponent(LastNameUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BirthdateUpdateLabel)
                    .addComponent(YearUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MONTHUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DAYupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PhoneUpdateLabel)
                    .addComponent(jLabel41)
                    .addComponent(PhoneCodeUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(PhoneUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(UpdateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CountryUpdateLabel)
                    .addComponent(CountryUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(UpdatePassengerButton)
                .addGap(113, 113, 113))
        );

        AirportTabbed.addTab("Update info", UpdateInfoPanel);

        idPassengerFlightText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idPassengerFlightText.setEnabled(false);

        IdAddUserFlightLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IdAddUserFlightLabel.setText("ID:");

        idFlightUserAddLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idFlightUserAddLabel.setText("Flight:");

        idFlightPassenger.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idFlightPassenger.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight" }));
        idFlightPassenger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFlightPassengerActionPerformed(evt);
            }
        });

        addPassengerFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addPassengerFlightButton.setText("Add");
        addPassengerFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPassengerFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddToFlightPanelLayout = new javax.swing.GroupLayout(AddToFlightPanel);
        AddToFlightPanel.setLayout(AddToFlightPanelLayout);
        AddToFlightPanelLayout.setHorizontalGroup(
            AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddToFlightPanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IdAddUserFlightLabel)
                    .addComponent(idFlightUserAddLabel))
                .addGap(79, 79, 79)
                .addGroup(AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idFlightPassenger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idPassengerFlightText, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(873, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddToFlightPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addPassengerFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(509, 509, 509))
        );
        AddToFlightPanelLayout.setVerticalGroup(
            AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddToFlightPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddToFlightPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(IdAddUserFlightLabel))
                    .addComponent(idPassengerFlightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(AddToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idFlightUserAddLabel)
                    .addComponent(idFlightPassenger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(addPassengerFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        AirportTabbed.addTab("Add to flight", AddToFlightPanel);

        showMyFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        showMyFlightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Departure Date", "Arrival Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(showMyFlightsTable);

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowMyFlightsPanelLayout = new javax.swing.GroupLayout(ShowMyFlightsPanel);
        ShowMyFlightsPanel.setLayout(ShowMyFlightsPanelLayout);
        ShowMyFlightsPanelLayout.setHorizontalGroup(
            ShowMyFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowMyFlightsPanelLayout.createSequentialGroup()
                .addGroup(ShowMyFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowMyFlightsPanelLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ShowMyFlightsPanelLayout.createSequentialGroup()
                        .addGap(519, 519, 519)
                        .addComponent(jButton2)))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        ShowMyFlightsPanelLayout.setVerticalGroup(
            ShowMyFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowMyFlightsPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        AirportTabbed.addTab("Show my flights", ShowMyFlightsPanel);

        passengerTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(passengerTable);

        RefreshPassengerTableButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshPassengerTableButtom.setText("Refresh");
        RefreshPassengerTableButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshPassengerTableButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowAllPassengersPanelLayout = new javax.swing.GroupLayout(ShowAllPassengersPanel);
        ShowAllPassengersPanel.setLayout(ShowAllPassengersPanelLayout);
        ShowAllPassengersPanelLayout.setHorizontalGroup(
            ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                .addGroup(ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(RefreshPassengerTableButtom))
                    .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        ShowAllPassengersPanelLayout.setVerticalGroup(
            ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowAllPassengersPanelLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RefreshPassengerTableButtom)
                .addContainerGap())
        );

        AirportTabbed.addTab("Show all passengers", ShowAllPassengersPanel);

        flightTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(flightTable);

        RefreshFlightsButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshFlightsButtom.setText("Refresh");
        RefreshFlightsButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshFlightsButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowAllFlightsPanelLayout = new javax.swing.GroupLayout(ShowAllFlightsPanel);
        ShowAllFlightsPanel.setLayout(ShowAllFlightsPanelLayout);
        ShowAllFlightsPanelLayout.setHorizontalGroup(
            ShowAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllFlightsPanelLayout.createSequentialGroup()
                .addGroup(ShowAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowAllFlightsPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ShowAllFlightsPanelLayout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(RefreshFlightsButtom)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        ShowAllFlightsPanelLayout.setVerticalGroup(
            ShowAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllFlightsPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RefreshFlightsButtom)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        AirportTabbed.addTab("Show all flights", ShowAllFlightsPanel);

        RefreshPlaneButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshPlaneButtom.setText("Refresh");
        RefreshPlaneButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshPlaneButtomActionPerformed(evt);
            }
        });

        planeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(planeTable);

        javax.swing.GroupLayout ShowAllPlanesPanelLayout = new javax.swing.GroupLayout(ShowAllPlanesPanel);
        ShowAllPlanesPanel.setLayout(ShowAllPlanesPanelLayout);
        ShowAllPlanesPanelLayout.setHorizontalGroup(
            ShowAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllPlanesPanelLayout.createSequentialGroup()
                .addGroup(ShowAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowAllPlanesPanelLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(RefreshPlaneButtom))
                    .addGroup(ShowAllPlanesPanelLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        ShowAllPlanesPanelLayout.setVerticalGroup(
            ShowAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowAllPlanesPanelLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(RefreshPlaneButtom)
                .addGap(17, 17, 17))
        );

        AirportTabbed.addTab("Show all planes", ShowAllPlanesPanel);

        locationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airport ID", "Airport Name", "City", "Country"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(locationTable);

        RefreshLocationButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshLocationButtom.setText("Refresh");
        RefreshLocationButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshLocationButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowAllLocationsPanelLayout = new javax.swing.GroupLayout(ShowAllLocationsPanel);
        ShowAllLocationsPanel.setLayout(ShowAllLocationsPanelLayout);
        ShowAllLocationsPanelLayout.setHorizontalGroup(
            ShowAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllLocationsPanelLayout.createSequentialGroup()
                .addGroup(ShowAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowAllLocationsPanelLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(RefreshLocationButtom))
                    .addGroup(ShowAllLocationsPanelLayout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(318, Short.MAX_VALUE))
        );
        ShowAllLocationsPanelLayout.setVerticalGroup(
            ShowAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowAllLocationsPanelLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(RefreshLocationButtom)
                .addGap(17, 17, 17))
        );

        AirportTabbed.addTab("Show all locations", ShowAllLocationsPanel);

        hoursDelay.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hoursDelay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        HourLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        HourLabel.setText("Hours:");

        IdFlightLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IdFlightLabel.setText("ID:");

        idFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idFlight.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));
        idFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFlightActionPerformed(evt);
            }
        });

        MinuteLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MinuteLabel.setText("Minutes:");

        minutesDelay.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        minutesDelay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        DelayButtom.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DelayButtom.setText("Delay");
        DelayButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelayButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DelayFlightPanelLayout = new javax.swing.GroupLayout(DelayFlightPanel);
        DelayFlightPanel.setLayout(DelayFlightPanelLayout);
        DelayFlightPanelLayout.setHorizontalGroup(
            DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DelayFlightPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DelayFlightPanelLayout.createSequentialGroup()
                        .addComponent(MinuteLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minutesDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DelayFlightPanelLayout.createSequentialGroup()
                        .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IdFlightLabel)
                            .addComponent(HourLabel))
                        .addGap(79, 79, 79)
                        .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hoursDelay, 0, 151, Short.MAX_VALUE)
                            .addComponent(idFlight, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(820, 820, 820))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DelayFlightPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DelayButtom)
                .addGap(531, 531, 531))
        );
        DelayFlightPanelLayout.setVerticalGroup(
            DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DelayFlightPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdFlightLabel)
                    .addComponent(idFlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HourLabel)
                    .addComponent(hoursDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(DelayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MinuteLabel)
                    .addComponent(minutesDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(DelayButtom)
                .addGap(33, 33, 33))
        );

        AirportTabbed.addTab("Delay flight", DelayFlightPanel);

        panelRound1.add(AirportTabbed, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 41, 1150, 620));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        panelRound1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 660, 1150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorActionPerformed
        if (user.isSelected()) {
            user.setSelected(false);
            userSelect.setSelectedIndex(0);

        }
        for (int i = 1; i < AirportTabbed.getTabCount(); i++) {
                AirportTabbed.setEnabledAt(i, true);
        }
        AirportTabbed.setEnabledAt(5, false);
        AirportTabbed.setEnabledAt(6, false);
    }//GEN-LAST:event_administratorActionPerformed

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        if (administrator.isSelected()) {
            administrator.setSelected(false);
        }
        for (int i = 1; i < AirportTabbed.getTabCount(); i++) {
            AirportTabbed.setEnabledAt(i, false);
        }
        AirportTabbed.setEnabledAt(9, true);
        AirportTabbed.setEnabledAt(5, true);
        AirportTabbed.setEnabledAt(6, true);
        AirportTabbed.setEnabledAt(7, true);
        AirportTabbed.setEnabledAt(11, true);
    }//GEN-LAST:event_userActionPerformed

    private void RegisterPassengerButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterPassengerButtomActionPerformed
    // 1. Obtener los campos como String (sin validar ni parsear)
    // Obtener los datos como String (sin validarlos ni parsearlos)
    String id = IdRegistrationTextField.getText();
    String firstname = FirstNameRegistrationTextField.getText();
    String lastname = LastNameRegistrationTextField.getText();
    String year = YearRegistrationTextField.getText();
    String month = MONTH.getSelectedItem().toString();
    String day = DAY.getSelectedItem().toString();
    String phoneCode = PhoneCodeRegistrationTextField.getText();
    String phone = PhoneRegistrationTextField.getText();
    String country = countryRegistrationTextField.getText();

    // 2. Construir fecha como string (formato YYYY-MM-DD)
    String birthDate = year + "-" + month + "-" + day;

    // 3. Llamar al controlador
    Response response = PassengerController.registerPassenger(
        id, firstname, lastname, birthDate, phoneCode, phone, country
    );

    // 4. Mostrar mensaje al usuario usando JOptionPane según código de estado
    if (response.getStatus() >= 500) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
    } else if (response.getStatus() >= 400) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Success " + response.getStatus(), JOptionPane.INFORMATION_MESSAGE);

        // 5. Si fue exitoso, limpiar los campos y actualizar combo
        IdRegistrationTextField.setText("");
        FirstNameRegistrationTextField.setText("");
        LastNameRegistrationTextField.setText("");
        YearRegistrationTextField.setText(""); // año
        MONTH.setSelectedIndex(0);
        DAY.setSelectedIndex(0);
        PhoneCodeRegistrationTextField.setText("");
        PhoneRegistrationTextField.setText("");
        countryRegistrationTextField.setText("");
        userSelect.addItem(id);
    }
    }//GEN-LAST:event_RegisterPassengerButtomActionPerformed

    private void createPlanejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPlanejButtonActionPerformed
        // TODO add your handling code here:
        String id = IdPlaneTextField.getText();
        String brand = BrandPlaneTextField.getText();
        String model = ModelPlaneTextField.getText();
        String maxCapacity = PlaneCapacityTextField11.getText();
        String airline = PlaneAirlinejTextField.getText();
        // 2. Llamar al controlador
    Response response = PlaneController.registerPlane(id, brand, model, maxCapacity, airline);

    // 3. Mostrar mensaje según resultado
    if (response.getStatus() >= 500) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
    } else if (response.getStatus() >= 400) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito " + response.getStatus(), JOptionPane.INFORMATION_MESSAGE);

        // 4. Limpiar campos si fue exitoso
        IdPlaneTextField.setText("");
        BrandPlaneTextField.setText("");
        ModelPlaneTextField.setText("");
        PlaneCapacityTextField11.setText("");
        PlaneAirlinejTextField.setText("");
        this.planeSelect.addItem(id);
}
    }//GEN-LAST:event_createPlanejButtonActionPerformed

    private void createLocationButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createLocationButtomActionPerformed
        // TODO add your handling code here:
        String id = airportId.getText();
        String name = nameAirport.getText();
        String city = cityAirport.getText();
        String country = countryAirport.getText();
        String latitude = latitudeAirport.getText();
        String longitude = longitudeAirport.getText();
        
        // Inicializar controlador local (NO en el constructor)
        LocationRepository locationRepository = AirportStorage.getInstance().getLocationRepository();
        LocationController locationController = new LocationController(locationRepository);

        
        Response response = locationController.createLocation(id, name, city, country, latitude, longitude);

        // Mostrar mensaje al usuario
        JOptionPane.showMessageDialog(null, response.getMessage());

        if (response.getStatus() == Status.CREATED) {
            airportId.setText("");
            nameAirport.setText("");
            cityAirport.setText("");
            countryAirport.setText("");
            latitudeAirport.setText("");
            longitudeAirport.setText("");

            DepartureLocationSelect.addItem(id);
            ArrivalLocationSelect.addItem(id);
            ScaleComboBox.addItem(id);
        }
    }//GEN-LAST:event_createLocationButtomActionPerformed

    private void FlightCreateButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightCreateButtomActionPerformed
        String id = idFlightTextField.getText();
        String planeId = planeSelect.getItemAt(planeSelect.getSelectedIndex());
        String departureLocationId = DepartureLocationSelect.getItemAt(DepartureLocationSelect.getSelectedIndex());
        String arrivalLocationId = ArrivalLocationSelect.getItemAt(ArrivalLocationSelect.getSelectedIndex());
        String scaleLocationId = ScaleComboBox.getItemAt(ScaleComboBox.getSelectedIndex());

        String year = añoTextField.getText();
        String month = MONTH1.getItemAt(MONTH1.getSelectedIndex());
        String day = DAY1.getItemAt(DAY1.getSelectedIndex());
        String hour = MONTH2.getItemAt(MONTH2.getSelectedIndex());
        String minutes = DAY2.getItemAt(DAY2.getSelectedIndex());
        String hoursArrival = MONTH3.getItemAt(MONTH3.getSelectedIndex());
        String minutesArrival = DAY3.getItemAt(DAY3.getSelectedIndex());
        String hoursScale = HourScale.getItemAt(HourScale.getSelectedIndex());
        String minutesScale = MinuteScale.getItemAt(MinuteScale.getSelectedIndex());

        
        String departureDateStr = String.format("%s-%s-%s", year, month, day);
        String departureTimeStr = String.format("%s:%s", hour, minutes);

        
        FlightController controller = new FlightController(flightRepo, locationRepo, planeRepo);

        Response response = controller.createFlight(
            id,
            departureLocationId,
            arrivalLocationId,
            departureDateStr,
            departureTimeStr,
            hoursArrival,
            minutesArrival,
            planeId,
            scaleLocationId,
            hoursScale,
            minutesScale
        );
        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success " + response.getStatus(), JOptionPane.INFORMATION_MESSAGE);

            idFlightTextField.setText("");   // ID del vuelo
            añoTextField.setText("");   // Año
            planeSelect.setSelectedIndex(0);
            DepartureLocationSelect.setSelectedIndex(0);
            ArrivalLocationSelect.setSelectedIndex(0);
            ScaleComboBox.setSelectedIndex(0);
            MONTH1.setSelectedIndex(0);
            DAY1.setSelectedIndex(0);
            MONTH2.setSelectedIndex(0);
            DAY2.setSelectedIndex(0);
            MONTH3.setSelectedIndex(0);
            DAY3.setSelectedIndex(0);
            HourScale.setSelectedIndex(0);
            MinuteScale.setSelectedIndex(0);
            idFlight.addItem(id);
            idFlightPassenger.addItem(id);
            
    }
    }//GEN-LAST:event_FlightCreateButtomActionPerformed

    private void UpdatePassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdatePassengerButtonActionPerformed
        // 1. Obtener los datos como String (sin validarlos ni parsearlos)
    String id = IdUpdateTextField.getText().trim();
    String firstname = FirstNameUpdateTextField.getText().trim();
    String lastname = LastNameUpdateTextField.getText().trim();
    String year = YearUpdateTextField.getText().trim(); // Año como texto
    String month = MONTHUpdate.getSelectedItem().toString(); // Combo box
    String day = DAYupdate.getSelectedItem().toString();     // Combo box
    String phoneCode = PhoneCodeUpdateTextField.getText().trim();
    String phone = PhoneUpdateTextField.getText().trim();
    String country = CountryUpdateTextField.getText().trim();

    // 2. Construir fecha como string (formato YYYY-MM-DD)
    // Construir fecha
            String birthDate = year + "-" + month + "-" + day;


    // 3. Llamar al controlador
    Response response = PassengerController.updatePassenger(
        id, firstname, lastname, birthDate, phoneCode, phone, country
    );

    // 4. Mostrar mensaje según el resultado
    if (response.getStatus() >= 500) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
    } else if (response.getStatus() >= 400) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        IdUpdateTextField.setText("");
        FirstNameUpdateTextField.setText("");
        LastNameUpdateTextField.setText("");
        YearUpdateTextField.setText("");
        MONTHUpdate.setSelectedIndex(0);
        DAYupdate.setSelectedIndex(0);
        PhoneCodeUpdateTextField.setText("");
        PhoneUpdateTextField.setText("");
        CountryUpdateTextField.setText("");
        }
    }//GEN-LAST:event_UpdatePassengerButtonActionPerformed

    private void addPassengerFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPassengerFlightButtonActionPerformed
        try {
            long passengerId = Long.parseLong(idPassengerFlightText.getText());
            String flightId = idFlightPassenger.getItemAt(idFlightPassenger.getSelectedIndex());

            FlightController controller = new FlightController(AirportStorage.getInstance().getFlightRepository(), AirportStorage.getInstance().getLocationRepository(),AirportStorage.getInstance().getPlaneRepo());

            Response response = controller.addPassengerToFlight(passengerId, flightId);

            if (response.getStatus() >= 500) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
            } else if (response.getStatus() >= 400) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Advertencia " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addPassengerFlightButtonActionPerformed

    private void DelayButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelayButtomActionPerformed
        // Obtener datos del formulario
    String flightId = idFlight.getItemAt(idFlight.getSelectedIndex());
    String hoursStr = hoursDelay.getItemAt(hoursDelay.getSelectedIndex());
    String minutesStr = minutesDelay.getItemAt(minutesDelay.getSelectedIndex());

    // Ejecutar acción con el controlador
    FlightController controller = new FlightController(flightRepo, locationRepo, planeRepo);
    Response response = controller.delayFlight(flightId, hoursStr, minutesStr);
    System.out.println("Vuelo " + flightId + " retrasado " + hoursStr + "h " + minutesStr + "m");

    // Mostrar mensaje
    if (response.getStatus() >= 500) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
    } else if (response.getStatus() >= 400) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Actualizar la tabla
        DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
        FlightTableList.updateFlightsList(model);

        // Resetear combos
        idFlight.setSelectedIndex(0);
        hoursDelay.setSelectedIndex(0);
        minutesDelay.setSelectedIndex(0);
    }
    }//GEN-LAST:event_DelayButtomActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    String passengerId = idPassengerFlightText.getText();
    
    Response response = PassengerFlightsTableList.updatePassengerFlightsList((DefaultTableModel) showMyFlightsTable.getModel(), passengerId);

    if (response.getStatus() >= 500) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
    } else if (response.getStatus() >= 400) {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Advertencia " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void RefreshPassengerTableButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshPassengerTableButtomActionPerformed
       Response response = PassengerTableList.updatePassengersList((DefaultTableModel) passengerTable.getModel());

       if (response.getStatus() >= 500) {
           JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
       } else if (response.getStatus() >= 400) {
           JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
       } else {
           JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_RefreshPassengerTableButtomActionPerformed

    private void RefreshFlightsButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshFlightsButtomActionPerformed
        Response r = FlightTableList.updateFlightsList((DefaultTableModel) flightTable.getModel());

        if (r.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (r.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_RefreshFlightsButtomActionPerformed

    private void RefreshPlaneButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshPlaneButtomActionPerformed
        Response r = PlaneTableList.updatePlanesList((DefaultTableModel) planeTable.getModel());

        if (r.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (r.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_RefreshPlaneButtomActionPerformed

    private void RefreshLocationButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshLocationButtomActionPerformed
        Response r = LocationTableList.updateLocationsList((DefaultTableModel) locationTable.getModel());

        if (r.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (r.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Error " + r.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_RefreshLocationButtomActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void IdRegistrationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdRegistrationTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdRegistrationTextFieldActionPerformed

    private void IdUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdUpdateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdUpdateTextFieldActionPerformed

    private void FirstNameRegistrationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstNameRegistrationTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstNameRegistrationTextFieldActionPerformed

    private void YearRegistrationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearRegistrationTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearRegistrationTextFieldActionPerformed

    private void YearUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearUpdateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearUpdateTextFieldActionPerformed

    private void planeSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planeSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_planeSelectActionPerformed

    private void ArrivalLocationSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArrivalLocationSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ArrivalLocationSelectActionPerformed

    private void DepartureLocationSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartureLocationSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartureLocationSelectActionPerformed

    private void idFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idFlightActionPerformed
        // ID VUELO
        String id = idFlight.getSelectedItem().toString();
            
    }//GEN-LAST:event_idFlightActionPerformed

    private void userSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSelectActionPerformed
        try {
            String id = userSelect.getSelectedItem().toString();
            if (! id.equals(userSelect.getItemAt(0))) {
                IdUpdateTextField.setText(id);
                idPassengerFlightText.setText(id);
            }
            else{
                IdUpdateTextField.setText("");
                idPassengerFlightText.setText("");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_userSelectActionPerformed

    private void idFlightPassengerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idFlightPassengerActionPerformed
        //ID VUELO
        String id = idFlightPassenger.getSelectedItem().toString();         
    }//GEN-LAST:event_idFlightPassengerActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddToFlightPanel;
    private javax.swing.JPanel AdministrationPanel;
    private javax.swing.JLabel AirlineLabel;
    private javax.swing.JLabel AirportCityLabel;
    private javax.swing.JLabel AirportCountryLabel;
    private javax.swing.JLabel AirportIdLabel;
    private javax.swing.JLabel AirportLatitudeLabel;
    private javax.swing.JLabel AirportLongitudeLabel;
    private javax.swing.JLabel AirportNameLabel;
    private javax.swing.JTabbedPane AirportTabbed;
    private javax.swing.JLabel ArrivalLocationLabel;
    private javax.swing.JComboBox<String> ArrivalLocationSelect;
    private javax.swing.JLabel BirthdateLabel;
    private javax.swing.JLabel BirthdateUpdateLabel;
    private javax.swing.JLabel BrandLabel;
    private javax.swing.JTextField BrandPlaneTextField;
    private javax.swing.JLabel CapacityLabel;
    private javax.swing.JLabel CountryUpdateLabel;
    private javax.swing.JTextField CountryUpdateTextField;
    private javax.swing.JComboBox<String> DAY;
    private javax.swing.JComboBox<String> DAY1;
    private javax.swing.JComboBox<String> DAY2;
    private javax.swing.JComboBox<String> DAY3;
    private javax.swing.JComboBox<String> DAYupdate;
    private javax.swing.JButton DelayButtom;
    private javax.swing.JPanel DelayFlightPanel;
    private javax.swing.JLabel DepartureDateLabel;
    private javax.swing.JLabel DepartureLocationLabel;
    private javax.swing.JComboBox<String> DepartureLocationSelect;
    private javax.swing.JLabel DurantionScaleLabel;
    private javax.swing.JLabel DurationArrivalLabel;
    private javax.swing.JLabel FirstNameLabel;
    private javax.swing.JTextField FirstNameRegistrationTextField;
    private javax.swing.JTextField FirstNameUpdateTextField;
    private javax.swing.JLabel FirstNameUpdateUserLabel;
    private javax.swing.JButton FlightCreateButtom;
    private javax.swing.JLabel HourLabel;
    private javax.swing.JComboBox<String> HourScale;
    private javax.swing.JLabel IdAddUserFlightLabel;
    private javax.swing.JLabel IdFlightLabel;
    private javax.swing.JTextField IdPlaneTextField;
    private javax.swing.JTextField IdRegistrationTextField;
    private javax.swing.JTextField IdUpdateTextField;
    private javax.swing.JLabel IdUpdateUserLabel;
    private javax.swing.JLabel LastNameLabel;
    private javax.swing.JTextField LastNameRegistrationTextField;
    private javax.swing.JLabel LastNameUpdateLabel;
    private javax.swing.JTextField LastNameUpdateTextField;
    private javax.swing.JPanel LocationRegistrationPanel;
    private javax.swing.JComboBox<String> MONTH;
    private javax.swing.JComboBox<String> MONTH1;
    private javax.swing.JComboBox<String> MONTH2;
    private javax.swing.JComboBox<String> MONTH3;
    private javax.swing.JComboBox<String> MONTHUpdate;
    private javax.swing.JLabel MinuteLabel;
    private javax.swing.JComboBox<String> MinuteScale;
    private javax.swing.JLabel ModelLabel;
    private javax.swing.JTextField ModelPlaneTextField;
    private javax.swing.JPanel PassengerRegistration;
    private javax.swing.JTextField PhoneCodeRegistrationTextField;
    private javax.swing.JTextField PhoneCodeUpdateTextField;
    private javax.swing.JTextField PhoneRegistrationTextField;
    private javax.swing.JLabel PhoneUpdateLabel;
    private javax.swing.JTextField PhoneUpdateTextField;
    private javax.swing.JTextField PlaneAirlinejTextField;
    private javax.swing.JTextField PlaneCapacityTextField11;
    private javax.swing.JPanel PlaneRegistrationPanel;
    private javax.swing.JButton RefreshFlightsButtom;
    private javax.swing.JButton RefreshLocationButtom;
    private javax.swing.JButton RefreshPassengerTableButtom;
    private javax.swing.JButton RefreshPlaneButtom;
    private javax.swing.JButton RegisterPassengerButtom;
    private javax.swing.JComboBox<String> ScaleComboBox;
    private javax.swing.JLabel ScaleLocationLabel;
    private javax.swing.JPanel ShowAllFlightsPanel;
    private javax.swing.JPanel ShowAllLocationsPanel;
    private javax.swing.JPanel ShowAllPassengersPanel;
    private javax.swing.JPanel ShowAllPlanesPanel;
    private javax.swing.JPanel ShowMyFlightsPanel;
    private javax.swing.JPanel UpdateInfoPanel;
    private javax.swing.JButton UpdatePassengerButton;
    private javax.swing.JTextField YearRegistrationTextField;
    private javax.swing.JTextField YearUpdateTextField;
    private javax.swing.JButton addPassengerFlightButton;
    private javax.swing.JRadioButton administrator;
    private javax.swing.JTextField airportId;
    private javax.swing.JTextField añoTextField;
    private javax.swing.JTextField cityAirport;
    private javax.swing.JTextField countryAirport;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JTextField countryRegistrationTextField;
    private javax.swing.JButton createLocationButtom;
    private javax.swing.JButton createPlanejButton;
    private javax.swing.JTable flightTable;
    private javax.swing.JComboBox<String> hoursDelay;
    private javax.swing.JComboBox<String> idFlight;
    private javax.swing.JLabel idFlightLabel;
    private javax.swing.JComboBox<String> idFlightPassenger;
    private javax.swing.JTextField idFlightTextField;
    private javax.swing.JLabel idFlightUserAddLabel;
    private javax.swing.JLabel idPasseneerRegisterLabel;
    private javax.swing.JTextField idPassengerFlightText;
    private javax.swing.JLabel idPlaneLabel;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField latitudeAirport;
    private javax.swing.JTable locationTable;
    private javax.swing.JTextField longitudeAirport;
    private javax.swing.JComboBox<String> minutesDelay;
    private javax.swing.JTextField nameAirport;
    private core.view.PanelRound panelRound1;
    private core.view.PanelRound panelRound2;
    private core.view.PanelRound panelRound3;
    private javax.swing.JTable passengerTable;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel planeFlightLabel;
    private javax.swing.JComboBox<String> planeSelect;
    private javax.swing.JTable planeTable;
    private javax.swing.JTable showMyFlightsTable;
    private javax.swing.JRadioButton user;
    private javax.swing.JComboBox<String> userSelect;
    // End of variables declaration//GEN-END:variables
}
