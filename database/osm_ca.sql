

create table osm_ca2 as select osm_id as p_id, name, way as geog, 'osm'::varchar(25) as provider, next_id() as w_id from planet_osm_point where 
(leisure is not null or  
amenity is not null or  
office is not null or  
shop is not null or   
tourism is not null or  
historic is not null or  
aeroway is not null or  
place is not null or  
sport is not null) and name is not null;

create table osm_ca3 as select * from osm_ca where p_id in (select p_id from osm_ca2);
alter table osm_ca3 alter column name type varchar(255);
alter table osm_ca3 alter column geog type geography(Point,4326);
alter table osm_ca3 alter column p_id type varchar(255);

pg_dump -t poi_ca osmdata | psql -d openpoi
insert into poibase (t_id, p_id, lat, lng, name, geog, provider, uri) select tid as t_id, osmid as p_id, lat, lng, name, st_geographyfromtext('POINT('||lng||' '||lat||')') as geog, 'osm' as provider, '-99' from canada_csv;

drop table tmp;
create table tmp as select name, case
  when leisure is null then amenity
  when amenity is null then office
  when office is null then shop
  when shop is null then tourism
  when tourism is null then historic
  when historic is null then aeroway
  when aeroway is null then sport
  when sport is null then "natural"
  else null
  end as a,
  case
  when leisure is null then 'amenity'
  when amenity is null then 'office'
  when office is null then 'shop'
  when shop is null then 'tourism'
  when tourism is null then 'historic'
  when historic is null then 'aeroway'
  when aeroway is null then 'sport'
  when sport is null then 'natural'
  else null
  end as b,
  place, "natural"
  from planet_osm_point 
  where name is not null;
  
  
  
create table tmp as select name, concat(leisure, amenity, office, shop, tourism, historic, aeroway, place, sport) from planet_osm_point where 
(leisure is not null or  
amenity is not null or  
office is not null or  
shop is not null or   
tourism is not null or  
historic is not null or  
aeroway is not null or  
place is not null or  
sport is not null) and name is not null;


ST_GeographyFromText('SRID=4326;POINT(' || lng || ' ' ||  lat || ')');