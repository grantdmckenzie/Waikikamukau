SELECT concat(w_id,'-',REPLACE(regexp_replace(trim(name), '[[:punct:]]', ''), ' ', '-')) as uri from poibase;


update poibase set uri = substring(concat(stringify_bigint(nextval('poibase_w_id_int_seq'::regclass)),'-',REPLACE(regexp_replace(trim(lower(name)), '[[:punct:]]', '','g'), ' ', '-')),0,50);

update poibase set uri = concat(stringify_bigint(nextval('poibase_w_id_int_seq'::regclass)),'-',name2uri(name));

CREATE OR REPLACE FUNCTION name2uri(text)
RETURNS text
IMMUTABLE
STRICT
LANGUAGE SQL
AS $$
SELECT substring(REPLACE(trim(regexp_replace(translate(
    lower($1),
    'áàâãäåāăąèééêëēĕėęěìíîïìĩīĭḩóôõöōŏőùúûüũūŭůäàáâãåæçćĉčöòóôõøüùúûßéèêëýñîìíïş',
    'aaaaaaaaaeeeeeeeeeeiiiiiiiihooooooouuuuuuuuaaaaaaeccccoooooouuuuseeeeyniiiis'
), '[^a-z0-9\-]+', ' ', 'g')),' ', '-'),0,60);
$$;