-- Active: 1786523789682@@127.0.0.1@5432@gestion-restaurant

INSERT INTO TABLE_ (idtable, actif, designation, occupation) VALUES
('T01', true, 'Table VIP 1', true),
('T02', true, 'Table VIP 2', false),
('T03', true, 'Terrasse 1', true),
('T04', true, 'Terrasse 2', false),
('T05', true, 'Salle Principale 1', false),
('T06', false, 'Salle Principale 2', false);


INSERT INTO MENU (idplat, actif, nomplat, pu) VALUES
('P01', true, 'Salade César', 12.50),
('P02', true, 'Burger Maison & Frites', 16.00),
('P03', true, 'Entrecôte 250g', 22.50),
('P04', true, 'Pâtes Carbonara', 14.00),
('P05', true, 'Pizza Margherita', 11.00),
('P06', true, 'Tiramisu', 6.50),
('P07', true, 'Fondant au Chocolat', 7.00),
('P08', true, 'Coca-Cola 33cl', 3.50),
('P09', true, 'Eau Minérale 1L', 4.00),
('P10', false, 'Cocktail Éphémère', 9.00);


INSERT INTO COMMANDE (idcom, idtable, nomcli, typecom, datecom, paye) VALUES
('CMD-001', 'T01', 'Jean Dupont', 'SUR_PLACE', '2026-07-20', true),
('CMD-002', 'T03', 'Marie Curie', 'SUR_PLACE', '2026-07-21', false),
('CMD-003', NULL,  'Lucie Martin', 'A_EMPORTER', '2026-07-21', true),
('CMD-004', 'T01', 'Paul Bernard', 'SUR_PLACE', '2026-07-21', false),
('CMD-005', NULL,  'Thomas Petit', 'LIVRAISON', '2026-07-21', false);


INSERT INTO LIGNE_COMMANDE (idligne, idcom, idplat, quantite) VALUES
('L01', 'CMD-001', 'P01', 1),
('L02', 'CMD-001', 'P03', 1),
('L03', 'CMD-001', 'P08', 2),
('L04', 'CMD-001', 'P06', 1),
('L05', 'CMD-002', 'P04', 2),
('L06', 'CMD-002', 'P09', 1),
('L07', 'CMD-003', 'P05', 3),
('L08', 'CMD-003', 'P08', 3),
('L09', 'CMD-004', 'P02', 2),
('L10', 'CMD-005', 'P05', 1);


INSERT INTO RESERVER (idreserv, idtable, nomcli, date_de_reserv, date_reserve) VALUES
('RES-001', 'T02', 'Sophie Durand', '2026-07-19 14:30:00', '2026-07-22 19:30:00'),
('RES-002', 'T04', 'Alexandre Leroy', '2026-07-20 10:15:00', '2026-07-22 20:00:00'),
('RES-003', 'T01', 'Émilie Roux', '2026-07-21 09:00:00', '2026-07-23 12:30:00');