CREATE DATABASE social_network;

\c social_network

CREATE TABLE utilisateur(
    id_utilisateur SERIAL PRIMARY KEY,
    nom VARCHAR(200),
    prenom VARCHAR(200) NOT NULL,
    genre CHAR(1) CHECK (genre IN ('M', 'F')),
    email VARCHAR(100) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id_message SERIAL primary key,
    envoyeur_id INT REFERENCES utilisateur(id_utilisateur),
    receveur_id INT REFERENCES utilisateur(id_utilisateur),
    message_text TEXT,
    message_date TIMESTAMP DEFAULT NOW(),
);


CREATE TABLE publication(
    id_publication SERIAL PRIMARY KEY,
    contenu TEXT
);

CREATE TABLE poste(
    id_poste SERIAL PRIMARY KEY,
    date_poste TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    auteurId INT REFERENCES utilisateur(id_utilisateur),
    publicationId INT REFERENCES publication(id_publication)
);

CREATE TABLE commentaire(
    id_commentaire SERIAL PRIMARY KEY,
    commentaire TEXT,
    commentaire_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    commentateurId INT REFERENCES utilisateur(id_utilisateur),
    publicationId INT REFERENCES publication(id_publication)
);
