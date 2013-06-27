CREATE TABLE scatter WITH DESCRIPTION 'A test table for scatterplots.'
ROW KEY FORMAT HASHED
WITH LOCALITY GROUP default (
    MAXVERSIONS = infinity,
    TTL = infinity,
    INMEMORY = false,
    COMPRESSED WITH gzip,
    GROUP TYPE FAMILY info WITH DESCRIPTION 'Basic info columns.' (
        COLUMN tempo WITH SCHEMA "int" WITH DESCRIPTION 'Music tempos.',
        COLUMN duration WITH SCHEMA "int" WITH DESCRIPTION 'Music durations.'
    )
);
