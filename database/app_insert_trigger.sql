CREATE OR REPLACE FUNCTION app_insert_trigger()
  RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    UPDATE poibase SET geog = st_geographyfromtext('POINT('||lng||' '||lat||')') WHERE geog is null;
    UPDATE poibase SET uri = concat(stringify_bigint(w_id_int),'-',name2uri(name)) WHERE uri = '';
    RETURN NEW;
END $$;




CREATE TRIGGER app_insert
  AFTER INSERT
  ON poibase
  FOR EACH ROW
  EXECUTE PROCEDURE app_insert_trigger();