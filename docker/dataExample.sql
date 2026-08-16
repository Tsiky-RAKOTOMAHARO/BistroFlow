-- Active: 1786906355568@@127.0.0.1@5432@gestion-restaurant

INSERT INTO TABLE_ (idtable, actif, designation, occupation) VALUES
('TO1', true, 'Table VIP 1', true),
('TO2', true, 'Table VIP 2', false),
('TO3', true, 'Terrasse 1', true),
('TO4', true, 'Terrasse 2', false),
('TO5', true, 'Salle Principale 1', false),
('TO6', false, 'Salle Principale 2', false);


INSERT INTO MENU (idplat, actif, nomplat, pu) VALUES
('PO1', true, 'Salade César', 12.50),
('PO2', true, 'Burger Maison & Frites', 16.00),
('PO3', true, 'Entrecôte 250g', 22.50),
('PO4', true, 'Pâtes Carbonara', 14.00),
('PO5', true, 'Pizza Margherita', 11.00),
('PO6', true, 'Tiramisu', 6.50),
('PO7', true, 'Fondant au Chocolat', 7.00),
('PO8', true, 'Coca-Cola 33cl', 3.50),
('PO9', true, 'Eau Minérale 1L', 4.00),
('PO10', false, 'Cocktail Éphémère', 9.00);


INSERT INTO COMMANDE (idcom, idtable, nomcli, typecom, datecom, paye) VALUES
('CMD-O1', 'TO1', 'Jean Dupont', 'SUR_PLACE', '2026-07-20', true),
('CMD-O2', 'TO3', 'Marie Curie', 'SUR_PLACE', '2026-07-21', false),
('CMD-O3', NULL,  'Lucie Martin', 'A_EMPORTER', '2026-07-21', true),
('CMD-O4', 'TO1', 'Paul Bernard', 'SUR_PLACE', '2026-07-21', false),
('CMD-O5', NULL,  'Thomas Petit', 'LIVRAISON', '2026-07-21', false);


INSERT INTO LIGNE_COMMANDE (idligne, idcom, idplat, quantite) VALUES
('L01', 'CMD-O1', 'P01', 1),
('L02', 'CMD-O1', 'P03', 1),
('L03', 'CMD-O1', 'P08', 2),
('L04', 'CMD-O1', 'P06', 1),
('L05', 'CMD-O2', 'P04', 2),
('L06', 'CMD-O2', 'P09', 1),
('L07', 'CMD-O3', 'P05', 3),
('L08', 'CMD-O3', 'P08', 3),
('L09', 'CMD-O4', 'P02', 2),
('L10', 'CMD-O5', 'P05', 1);


INSERT INTO RESERVER (idreserv, idtable, nomcli, date_de_reserv, date_reserve) VALUES
('RES-O1', 'TO2', 'Sophie Durand', '2026-07-19 14:30:00', '2026-07-22 19:30:00'),
('RES-O2', 'TO4', 'Alexandre Leroy', '2026-07-20 10:15:00', '2026-07-22 20:00:00'),
('RES-O3', 'TO1', 'Émilie Roux', '2026-07-21 09:00:00', '2026-07-23 12:30:00');