DROP DATABASE IF EXISTS ProgettoTSW;
CREATE DATABASE ProgettoTSW;
USE ProgettoTSW;

-- =============================================
-- TABELLA: UTENTE
-- =============================================
CREATE TABLE Utente (
    email VARCHAR(100) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL  -- In produzione: salvare hash, non password in chiaro
);

-- =============================================
-- TABELLA: PRODOTTO
-- =============================================
CREATE TABLE Prodotto (
    codice_prodotto INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT,
    costo FLOAT NOT NULL,
    immagine VARCHAR(255),  -- Path dell'immagine o BLOB
    quantita_magazzino INT NOT NULL DEFAULT 0
);

-- =============================================
-- TABELLA: CATEGORIA
-- =============================================
CREATE TABLE Categoria (
    nome VARCHAR(50) PRIMARY KEY NOT NULL UNIQUE
);

-- =============================================
-- TABELLA: ORDINE
-- =============================================
CREATE TABLE Ordine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email_utente VARCHAR(100) NOT NULL,
    data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totale_ordine FLOAT NOT NULL,
    civico VARCHAR(10) NOT NULL,
    cap VARCHAR(10) NOT NULL,
    città VARCHAR(50) NOT NULL,
    via VARCHAR(50) NOT NULL,
    CVV SMALLINT NOT NULL,
    pan BIGINT NOT NULL,
    scadenza DATE NOT NULL,
    stato VARCHAR(50) DEFAULT 'In elaborazione',
    FOREIGN KEY (email_utente) REFERENCES Utente(email) ON DELETE CASCADE
);

-- =============================================
-- TABELLA: POSSIEDE (Relazione Prodotto - Categoria)
-- =============================================
CREATE TABLE Possiede (
    prodotto_id INT NOT NULL,
    categoria_nome VARCHAR(50) NOT NULL,
    PRIMARY KEY (prodotto_id, categoria_nome),
    FOREIGN KEY (prodotto_id) REFERENCES Prodotto(codice_prodotto) ON DELETE CASCADE,
    FOREIGN KEY (categoria_nome) REFERENCES Categoria(nome) ON DELETE CASCADE
);


