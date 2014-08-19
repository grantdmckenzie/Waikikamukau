

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

pg_dump -t osm_ca3 osmdata | psql -d openpoi



create table tmp as select name, case
  when leisure is null then amenity
  when amenity is null then office
  when office is null then shop
  when shop is null then tourism
  when tourism is null then historic
  when historic is null then aeroway
  when aeroway is null then place
  when place is null then sport
  else null
  end,
  case
  when leisure is null then amenity
  when amenity is null then office
  when office is null then shop
  when shop is null then tourism
  when tourism is null then historic
  when historic is null then aeroway
  when aeroway is null then place
  when place is null then sport
  else null
  end
  from planet_osm_point
  where name is not null;