create table pronostico.t_planetas
(
id_planeta int not null AUTO_INCREMENT,
nombre_planeta varchar(50) not null ,
velocidad int not null,
radio int not null,
rotacion int not null,
PRIMARY KEY ( id_planeta )
);

create table pronostico.t_climas
(
id_clima int not null,
clima varchar(10) not null,
PRIMARY KEY ( id_clima )
);

create table pronostico.t_planeta_clima
(
id_planeta_clima int not null AUTO_INCREMENT,
id_planeta int not null,
id_clima int not null,
dia int not null,
coordenadaY double not null,
coordenadaX double not null,
PRIMARY KEY (id_planeta_clima ),
FOREIGN KEY (id_planeta) REFERENCES pronostico.t_planetas(id_planeta),
FOREIGN KEY (id_clima) REFERENCES pronostico.t_climas(id_clima)
);


create table pronostico.t_clima_sistema_solar
(
id_climaSistemaSolar int not null AUTO_INCREMENT,
id_clima int not null,
dia int not null,
perimetro_triangulo double,
PRIMARY KEY (id_climaSistemaSolar),
FOREIGN KEY (id_clima) REFERENCES pronostico.t_climas(id_clima)
);