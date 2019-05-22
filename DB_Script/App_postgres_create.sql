CREATE TABLE user_details (
	User_Id serial NOT NULL ,
	First_Name character varying(50) NOT NULL,
	Last_Name character varying(50) NOT NULL,
	Email_Id character varying(100) NOT NULL UNIQUE,
	Phone_Number int4 NOT NULL,
	Auth_Code character varying(100) NOT NULL,
	State character varying(100) NOT NULL,
	Country character varying(100) NOT NULL,
	is_active bpchar(1) NOT NULL DEFAULT 'N'::bpchar,
	CONSTRAINT user_details_email_id_key UNIQUE (email_id),
	CONSTRAINT User_Details_pk PRIMARY KEY (User_Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE post_details (
	Post_Id serial NOT NULL,
	Title character varying(100) NOT NULL,
	Content character varying(500) NOT NULL,
	Created_By int4 NOT NULL,
	Created_Date DATE NOT NULL,
	Updated_By int4 NOT NULL,
	Updated_Date DATE NOT NULL,
	CONSTRAINT Post_Details_pk PRIMARY KEY (Post_Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE post_images (
	Image_Id serial NOT NULL,
	Image_Name character varying(100) NOT NULL,
	Image_Size int4 NOT NULL,
	Image_FilePath character varying(200) NOT NULL,
	Post_Id int4 NOT NULL,
	CONSTRAINT Post_Images_pk PRIMARY KEY (Image_Id)
) WITH (
  OIDS=FALSE
);


CREATE TABLE destination_details (
	Destination_Id serial NOT NULL,
	Destination_Country character varying(100) NOT NULL,
	Destination_State character varying(100),
	Destination_Name character varying(100) NOT NULL,
	Destination_Latitude character varying(100) NOT NULL,
	Destination_Longitude character varying(100) NOT NULL,
	CONSTRAINT Destination_Details_pk PRIMARY KEY (Destination_Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE user_destination_visited (
	User_Destination_Id serial NOT NULL,
	Destination_Id int4 NOT NULL,
	Visited_By int4 NOT NULL,
	Visiting_Date DATE NOT NULL,
	CONSTRAINT User_Destination_Visited_pk PRIMARY KEY (User_Destination_Id)
) WITH (
  OIDS=FALSE
);







CREATE TABLE itinerary_details (
	Itinerary_id serial NOT NULL,
	Itinerary_Title character varying NOT NULL,
	Itinerary_FilePath character varying(200) NOT NULL,
	User_Destination_Id int4 NOT NULL,
	CONSTRAINT Itinerary_Details_pk PRIMARY KEY (Itinerary_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE hotel_details (
	Hotel_Id serial NOT NULL,
	Hotel_Name character varying(100) NOT NULL,
	Hotel_Latitude character varying(100) NOT NULL,
	Hotel_Longitude character varying(100) NOT NULL,
	Destination_Id int4 NOT NULL,
	CONSTRAINT Hotel_Details_pk PRIMARY KEY (Hotel_Id)
) WITH (
  OIDS=FALSE
);



ALTER SEQUENCE User_Details_User_Id_seq RESTART WITH 1000;


ALTER TABLE Post_Details ADD CONSTRAINT Post_Details_fk0 FOREIGN KEY (Created_By) REFERENCES User_Details(User_Id);
ALTER TABLE Post_Details ADD CONSTRAINT Post_Details_fk1 FOREIGN KEY (Updated_By) REFERENCES User_Details(User_Id);

ALTER TABLE Post_Images ADD CONSTRAINT Post_Images_fk0 FOREIGN KEY (Post_Id) REFERENCES Post_Details(Post_Id);

ALTER TABLE User_Destination_Visited ADD CONSTRAINT User_Destination_Visited_fk0 FOREIGN KEY (Destination_Id) REFERENCES Destination_Details(Destination_Id);
ALTER TABLE User_Destination_Visited ADD CONSTRAINT User_Destination_Visited_fk1 FOREIGN KEY (Visited_By) REFERENCES User_Details(User_Id);


ALTER TABLE Itinerary_Details ADD CONSTRAINT Itinerary_Details_fk0 FOREIGN KEY (User_Destination_Id) REFERENCES User_Destination_Visited(User_Destination_Id);

ALTER TABLE Hotel_Details ADD CONSTRAINT Hotel_Details_fk0 FOREIGN KEY (Destination_Id) REFERENCES Destination_Details(Destination_Id);

