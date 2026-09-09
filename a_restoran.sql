USE sa_seminarski_restoran;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS stavkaracuna;
DROP TABLE IF EXISTS racun;
DROP TABLE IF EXISTS konobarsmena;
DROP TABLE IF EXISTS gost;
DROP TABLE IF EXISTS artikal;
DROP TABLE IF EXISTS smena;
DROP TABLE IF EXISTS konobar;
DROP TABLE IF EXISTS kategorijagosta;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE kategorijagosta (
  idKategorijaGosta INT NOT NULL AUTO_INCREMENT,
  opis VARCHAR(100) NOT NULL,
  popust DOUBLE NOT NULL DEFAULT 0,
  imaPopust TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (idKategorijaGosta)
);

CREATE TABLE konobar (
  idKonobar INT NOT NULL AUTO_INCREMENT,
  ime VARCHAR(50) NOT NULL,
  prezime VARCHAR(50) NOT NULL,
  korisnickoIme VARCHAR(50) NOT NULL,
  sifra VARCHAR(50) NOT NULL,
  PRIMARY KEY (idKonobar),
  UNIQUE KEY uq_konobar_korisnickoIme (korisnickoIme)
);

CREATE TABLE smena (
  idSmena INT NOT NULL AUTO_INCREMENT,
  naziv VARCHAR(50) NOT NULL,
  vremePocetka TIME NOT NULL,
  vremeKraja TIME NULL,
  PRIMARY KEY (idSmena)
);

CREATE TABLE artikal (
  idArtikal INT NOT NULL AUTO_INCREMENT,
  naziv VARCHAR(100) NOT NULL,
  cena DOUBLE NOT NULL,
  tip VARCHAR(50) NOT NULL,
  PRIMARY KEY (idArtikal)
);

CREATE TABLE gost (
  idGost INT NOT NULL AUTO_INCREMENT,
  ime VARCHAR(50) NOT NULL,
  prezime VARCHAR(50) NOT NULL,
  idKategorijaGosta INT NOT NULL,
  PRIMARY KEY (idGost),
  CONSTRAINT fk_gost_kategorija
    FOREIGN KEY (idKategorijaGosta) REFERENCES kategorijagosta (idKategorijaGosta)
);

CREATE TABLE konobarsmena (
  idKonobar INT NOT NULL,
  idSmena INT NOT NULL,
  datumSmene DATE NOT NULL,
  PRIMARY KEY (idKonobar, idSmena, datumSmene),
  CONSTRAINT fk_ks_konobar
    FOREIGN KEY (idKonobar) REFERENCES konobar (idKonobar),
  CONSTRAINT fk_ks_smena
    FOREIGN KEY (idSmena) REFERENCES smena (idSmena)
);

CREATE TABLE racun (
  idRacun INT NOT NULL AUTO_INCREMENT,
  datumIzdavanja DATE NULL,
  vremeIzdavanja TIME NULL,
  ukupanIznos DOUBLE NOT NULL DEFAULT 0,
  jeIzdat TINYINT(1) NOT NULL DEFAULT 0,
  idKonobar INT NOT NULL,
  idGost INT NOT NULL,
  PRIMARY KEY (idRacun),
  CONSTRAINT fk_racun_konobar
    FOREIGN KEY (idKonobar) REFERENCES konobar (idKonobar),
  CONSTRAINT fk_racun_gost
    FOREIGN KEY (idGost) REFERENCES gost (idGost)
);

CREATE TABLE stavkaracuna (
  idRacun INT NOT NULL,
  rb INT NOT NULL,
  kolicina INT NOT NULL,
  cena DOUBLE NOT NULL,
  ukupanIznos DOUBLE NOT NULL,
  idArtikal INT NOT NULL,
  PRIMARY KEY (idRacun, rb),
  CONSTRAINT fk_stavka_racun
    FOREIGN KEY (idRacun) REFERENCES racun (idRacun)
    ON DELETE CASCADE,
  CONSTRAINT fk_stavka_artikal
    FOREIGN KEY (idArtikal) REFERENCES artikal (idArtikal)
);

INSERT INTO konobar (ime, prezime, korisnickoIme, sifra) VALUES
('Filip', 'Oketić', 'admin', 'admin');

INSERT INTO kategorijagosta (opis, popust, imaPopust) VALUES
('Običan', 0, 0),
('VIP', 10, 1),
('Student', 15, 1);

INSERT INTO smena (naziv, vremePocetka, vremeKraja) VALUES
('Jutarnja', '08:00:00', '16:00:00'),
('Popodnevna', '16:00:00', '00:00:00');

INSERT INTO artikal (naziv, cena, tip) VALUES
('Pepsi', 250, 'Pice'),
('Espresso', 180, 'Pice'),
('Ćevapi', 890, 'Jelo'),
('Pizza margarita', 990, 'Jelo');

INSERT INTO gost (ime, prezime, idKategorijaGosta) VALUES
('Marko', 'Marković', 1),
('Ana', 'Anić', 2);
