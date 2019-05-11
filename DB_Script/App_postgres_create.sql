CREATE TABLE "User_Details" (
	"User_Id" serial NOT NULL DEFAULT '1000',
	"First_Name" character varying(50) NOT NULL,
	"Last_Name" character varying(50) NOT NULL,
	"Email_Id" character varying(100) NOT NULL UNIQUE,
	"Phone_Number" numeric NOT NULL,
	"Auth_Code" character varying(100) NOT NULL,
	"State" character varying(100) NOT NULL,
	"Country" character varying(100) NOT NULL,
	CONSTRAINT User_Details_pk PRIMARY KEY ("User_Id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Post_Details" (
	"Post_Id" serial NOT NULL,
	"Title" character varying(100) NOT NULL,
	"Content" character varying(500) NOT NULL,
	"Created_By" numeric NOT NULL,
	"Created_Date" DATE NOT NULL,
	"Updated_By" numeric NOT NULL,
	"Updated_Date" DATE NOT NULL,
	CONSTRAINT Post_Details_pk PRIMARY KEY ("Post_Id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Post_Images" (
	"Image_Id" serial NOT NULL,
	"Image_Name" character varying(100) NOT NULL,
	"Image_Size" numeric NOT NULL,
	"Image_FilePath" character varying(200) NOT NULL,
	"Post_Image_Id" numeric NOT NULL,
	CONSTRAINT Post_Images_pk PRIMARY KEY ("Image_Id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "User_Destination_Visited" (
	"User_Destination_Id" serial NOT NULL,
	"Destination_Id" numeric NOT NULL,
	"Visited_By" numeric NOT NULL,
	"Visiting_Date" DATE NOT NULL,
	CONSTRAINT User_Destination_Visited_pk PRIMARY KEY ("User_Destination_Id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Destination_Details" (
	"Destination_Id" serial NOT NULL,
	"Destination_Country" character varying(100) NOT NULL,
	"Destination_State" character varying(100),
	"Destination_Name" character varying(100) NOT NULL,
	"Destination_Latitude" character varying(100) NOT NULL,
	"Destination_Longitude" character varying(100) NOT NULL,
	CONSTRAINT Destination_Details_pk PRIMARY KEY ("Destination_Id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Itinerary_Details" (
	"Itinerary_id" serial NOT NULL,
	"Itinerary_Title" character varying NOT NULL,
	"Itinerary_FilePath" character varying(200) NOT NULL,
	"User_Destination_Id" numeric NOT NULL,
	CONSTRAINT Itinerary_Details_pk PRIMARY KEY ("Itinerary_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Hotel_Details" (
	"Hotel_Id" serial NOT NULL,
	"Hotel_Name" character varying(100) NOT NULL,
	"Hotel_Latitude" character varying(100) NOT NULL,
	"Hotel_Longitude" character varying(100) NOT NULL,
	"Hotel_Destination_Id" numeric NOT NULL,
	CONSTRAINT Hotel_Details_pk PRIMARY KEY ("Hotel_Id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "Post_Details" ADD CONSTRAINT "Post_Details_fk0" FOREIGN KEY ("Created_By") REFERENCES "User_Details"("User_Id");
ALTER TABLE "Post_Details" ADD CONSTRAINT "Post_Details_fk1" FOREIGN KEY ("Updated_By") REFERENCES "User_Details"("User_Id");

ALTER TABLE "Post_Images" ADD CONSTRAINT "Post_Images_fk0" FOREIGN KEY ("Post_Image_Id") REFERENCES "Post_Details"("Post_Id");

ALTER TABLE "User_Destination_Visited" ADD CONSTRAINT "User_Destination_Visited_fk0" FOREIGN KEY ("Destination_Id") REFERENCES "Destination_Details"("Destination_Id");
ALTER TABLE "User_Destination_Visited" ADD CONSTRAINT "User_Destination_Visited_fk1" FOREIGN KEY ("Visited_By") REFERENCES "User_Details"("User_Id");


ALTER TABLE "Itinerary_Details" ADD CONSTRAINT "Itinerary_Details_fk0" FOREIGN KEY ("User_Destination_Id") REFERENCES "User_Destination_Visited"("User_Destination_Id");

ALTER TABLE "Hotel_Details" ADD CONSTRAINT "Hotel_Details_fk0" FOREIGN KEY ("Hotel_Destination_Id") REFERENCES "Destination_Details"("Destination_Id");

