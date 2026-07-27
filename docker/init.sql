CREATE TABLE TABLE_(
    idtable VARCHAR(25) PRIMARY KEY,
    actif BOOLEAN NOT NULL DEFAULT true,
    designation VARCHAR(25) NOT NULL,
    occupation BOOLEAN
);

CREATE TABLE MENU(
    idplat VARCHAR(25) PRIMARY KEY, 
    actif BOOLEAN NOT NULL DEFAULT true,
    nomplat VARCHAR(50) NOT NULL,
    pu INT NOT NULL 
);

CREATE TABLE COMMANDE(
    idcom VARCHAR(25) PRIMARY KEY,
    idtable VARCHAR(25),
    nomcli VARCHAR(150) NOT NULL,
    typecom VARCHAR(25) NOT NULL,
    datecom TIMESTAMP NOT NULL,
    paye BOOLEAN DEFAULT false,

    CONSTRAINT FK_commande_table
        FOREIGN KEY (idtable) REFERENCES TABLE_(idtable)
); 

CREATE TABLE LIGNE_COMMANDE(
    idligne VARCHAR(25) PRIMARY KEY,
    idcom VARCHAR(25),
    idplat VARCHAR(25),
    quantite INT NOT NULL,

    CONSTRAINT FK_ligne_commande_menu
        FOREIGN KEY (idplat) REFERENCES MENU(idplat),

    CONSTRAINT FK_ligne_commande_commande
        FOREIGN KEY (idcom) REFERENCES COMMANDE(idcom)
);

CREATE TABLE RESERVER(
    idreserv VARCHAR(25) PRIMARY KEY, 
    idtable VARCHAR(25),
    nomcli VARCHAR(150) NOT NULL,
    date_de_reserv TIMESTAMP, 
    date_reserve TIMESTAMP,    

    CONSTRAINT FK_reserver_table
        FOREIGN KEY (idtable) REFERENCES TABLE_(idtable)
);