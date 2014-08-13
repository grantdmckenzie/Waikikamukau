DROP TYPE w_loc CASCADE;
CREATE TYPE w_loc AS
(
  id varchar(255),
  name varchar(500),
  lat double precision,
  lng double precision,
  distance double precision
);
alter type w_loc owner to openpoi;



-- DROP FUNCTION w_nearby(double precision, double precision);

CREATE OR REPLACE FUNCTION w_nearby(double precision, double precision)
  RETURNS SETOF w_loc AS
$BODY$  
DECLARE rec record;  
d_lng ALIAS FOR $1;
d_lat ALIAS FOR $2;

BEGIN  
 FOR rec IN (select w_id, name, ST_Y(ST_GeomFromText(ST_AsText(geog))), ST_X(ST_GeomFromText(ST_AsText(geog))), st_distance(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')')) as dist from poibase where name is not null and ST_DWithin(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')'), 1000) order by dist limit 60) LOOP  
  RETURN NEXT rec;  
 END LOOP;  
END;  
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION w_nearby(double precision, double precision)
  OWNER TO openpoi;
  