DROP TYPE w_loc CASCADE;
CREATE TYPE w_loc AS
(
  id varchar(32),
  name varchar(500),
  cat int,
  lat double precision,
  lng double precision,
  distance double precision,
  uri text
);
alter type w_loc owner to openpoi;



-- DROP FUNCTION w_nearby(double precision, double precision);

CREATE OR REPLACE FUNCTION w_nearby(double precision, double precision, varchar(255))
  RETURNS SETOF w_loc AS
$BODY$  
DECLARE rec record;  
d_lng ALIAS FOR $1;
d_lat ALIAS FOR $2;

BEGIN  
 IF length($3) > 0 THEN
  FOR rec IN (select w_id, name, t_id, lat, lng, round(st_distance(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')'))*100)/100 as dist, concat(w_id,'-',REPLACE(regexp_replace(trim(lower(name)), '[[:punct:]]', '','g'), ' ', '-')) as uri 
  from poibase where ST_DWithin(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')'), 1000) and name ilike '%'||$3||'%'
  order by dist limit 60) 
  LOOP  
    RETURN NEXT rec;  
  END LOOP;  
 ELSE
  FOR rec IN (select w_id, name, t_id, lat, lng, round(st_distance(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')'))*100)/100 as dist, concat(w_id,'-',REPLACE(regexp_replace(trim(lower(name)), '[[:punct:]]', '','g'), ' ', '-')) as uri 
  from poibase where ST_DWithin(geog, st_geographyfromtext('POINT('||d_lng||' '||d_lat||')'), 1000) 
  order by dist limit 60) 
  LOOP  
    RETURN NEXT rec;  
  END LOOP;
 END IF;
END;  
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION w_nearby(double precision, double precision, varchar(255))
  OWNER TO openpoi;
  
