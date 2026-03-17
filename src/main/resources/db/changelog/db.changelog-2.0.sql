--liquibase formatted sql

--changeset salpa:1
INSERT INTO skin.collection(name, image)
VALUES ('Carbon','Carbon.png'),
       ('Dragon','Dragon.png');

--changeset salpa:2
INSERT INTO skin.skin(name, image, gun, rarely, collection_id)
VALUES ('G22 Carbon', 'G22_Carbon.png','GLOCK22','RARE', (SELECT id FROM skin.collection c WHERE c.name='Carbon')),
       ('AKR Carbon','AKR_Carbon.png','AKR','RARE',(SELECT id FROM skin.collection c WHERE c.name='Carbon')),
       ('AKR Dragon','AKR_Dragon.png','AKR','LEGENDARY',(SELECT id FROM skin.collection c WHERE c.name='Dragon')),
       ('AWP Dragon','AWP_Dragon.png','AWP','LEGENDARY',(SELECT id FROM skin.collection c WHERE c.name='Dragon'));