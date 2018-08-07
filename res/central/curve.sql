
Drop table partie CASCADE;
Drop table player CASCADE;
Drop table player_in_partie CASCADE;
Drop table manche CASCADE;

CREATE TABLE  partie (
  id_partie   serial,
  id_player serial  NOT NULL,
  ip_serveur varchar(50) NOT NULL,
  port varchar(50) NOT NULL,
  date date NOT NULL,
  complet boolean NOT NULL,
  PRIMARY KEY (id_partie)
) ;

-- --------------------------------------------------------

--
-- Structure de la table `player`
--

CREATE TABLE player (
  id_player serial ,
  pseudo varchar(50) UNIQUE NOT NULL,
  pays varchar(200) NOT NULL,
  rang_Elo int NOT NULL,
  password varchar(250) NOT NULL,
  date_creation date NOT NULL,
  PRIMARY KEY (id_player)
  
);

-- --------------------------------------------------------

--
-- Structure de la table `player_in_partie`
--

CREATE TABLE player_in_partie (
  id_partie serial ,
  id_player serial NOT NULL,
  points int NOT NULL,
  rang int NOT NULL,
  PRIMARY KEY (id_partie,id_player)) ;
-- Structure de la table `manche`
--
CREATE TABLE manche (
id_partie serial ,
id_player serial ,
numero_manche int NOT NULL,
eliminatedby int Not NULL,
PRIMARY KEY (id_partie,id_player,numero_manche ) 
);
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `partie`
--
ALTER TABLE partie
  ADD CONSTRAINT partie_ibfk_1 FOREIGN KEY (id_player) REFERENCES player (id_player);

--
-- Contraintes pour la table `player_in_partie`
--
ALTER TABLE player_in_partie
  ADD CONSTRAINT player_in_partie_ibfk_2 FOREIGN KEY (id_player) REFERENCES player (id_player),
  ADD CONSTRAINT player_in_partie_ibfk_1 FOREIGN KEY (id_partie) REFERENCES partie (id_partie);
--
-- Contraintes pour la table `manche`
--
ALTER TABLE manche
  ADD CONSTRAINT manche_ibfk_2 FOREIGN KEY (id_player) REFERENCES player (id_player),
  ADD CONSTRAINT manche_partie_ibfk_1 FOREIGN KEY (id_partie) REFERENCES partie (id_partie);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
