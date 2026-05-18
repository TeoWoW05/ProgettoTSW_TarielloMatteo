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
    password VARCHAR(255) NOT NULL  -- In produzione: salvare hash, non password in chiaro
);

-- =============================================
-- TABELLA: PRODOTTO
-- =============================================
CREATE TABLE Prodotto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT,
    costo DECIMAL(10,2) NOT NULL,
    immagine VARCHAR(255),  -- Path dell'immagine o BLOB
    quantita_magazzino INT NOT NULL DEFAULT 0,
    codice_prodotto VARCHAR(50) UNIQUE NOT NULL
);

-- =============================================
-- TABELLA: CATEGORIA
-- =============================================
CREATE TABLE Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descrizione TEXT,
    sito_web VARCHAR(255)  -- URL del sito della categoria
);

-- =============================================
-- TABELLA: ORDINE
-- =============================================
CREATE TABLE Ordine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email_utente VARCHAR(100) NOT NULL,
    data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totale DECIMAL(10,2) NOT NULL,
    indirizzo_spedizione VARCHAR(255) NOT NULL,
    civico VARCHAR(10) NOT NULL,
    cap VARCHAR(10) NOT NULL,
    città VARCHAR(50) NOT NULL,
    provincia VARCHAR(2) NOT NULL,
    stato VARCHAR(50) DEFAULT 'In elaborazione',
    FOREIGN KEY (email_utente) REFERENCES Utente(email) ON DELETE CASCADE
);

-- =============================================
-- TABELLA: POSSIEDE (Relazione Prodotto - Categoria)
-- =============================================
CREATE TABLE Possiede (
    prodotto_id INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (prodotto_id, categoria_id),
    FOREIGN KEY (prodotto_id) REFERENCES Prodotto(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id) ON DELETE CASCADE
);

-- =============================================
-- TABELLA: DETTAGLIO_ORDINE (Prodotti in un ordine)
-- =============================================
CREATE TABLE Dettaglio_Ordine (
    ordine_id INT NOT NULL,
    prodotto_id INT NOT NULL,
    quantita INT NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,  -- Prezzo al momento dell'acquisto
    PRIMARY KEY (ordine_id, prodotto_id),
    FOREIGN KEY (ordine_id) REFERENCES Ordine(id) ON DELETE CASCADE,
    FOREIGN KEY (prodotto_id) REFERENCES Prodotto(id) ON DELETE CASCADE
);
