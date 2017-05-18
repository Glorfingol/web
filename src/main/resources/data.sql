MERGE INTO news_entry (id,author,title,tags,image_id,content_id,facebook_id,creation_date,modification_date) 
VALUES                (4819767024441597184,'TEST','Un Test vide','test;fun',null,null,null,'2017-04-03 00:00:00','2017-04-03 00:00:00');

MERGE INTO news_entry (id,author,title,tags,image_id,content_id,facebook_id,creation_date,modification_date) 
VALUES                (4819767024441597195,'TEST','Un Test avec contenu','test;fun;yolo',null,4819767024441597196,null,'2017-04-03 00:00:00','2017-04-03 00:00:00');


MERGE INTO news_content (id,content,creation_date,modification_date) 
VALUES                (4819767024441597196,'Contenu de test','2017-04-03 00:00:00','2017-04-03 00:00:00');



