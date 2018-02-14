HAWK (original from 2015, ESWC paper)
====


Hybrid Question Answering (hawk) -- is going to drive forth the OKBQA vision of hybrid question answering using Linked Data and full-text indizes. 

Performance benchmarks can be done on the QALD-5 hybrid benchmark (test+train)

For more information on newer Question Answering related topic please see https://github.com/dice-group/NLIWOD/

Restful Service
===
``curl localhost:8080/search?q=What+is+the+capital+of+Germany+%3F``
will return a UUID.


``curl http://localhost:8080/status?UUID=00000000-0000-0000-0000-000000000001`` gives you status updates

Building HAWK
===
```
mvn clean package -DskipTests
java -jar target/hawk-0.1.0.jar
```

Citation 
===
```
===
@inproceedings{DBLP:conf/esws/UsbeckNBU15,
  author    = {Ricardo Usbeck and
               Axel{-}Cyrille Ngonga Ngomo and
               Lorenz B{\"{u}}hmann and
               Christina Unger},
  title     = {{HAWK} - Hybrid Question Answering Using Linked Data},
  booktitle = {The Semantic Web. Latest Advances and New Domains - 12th European
               Semantic Web Conference, {ESWC} 2015, Portoroz, Slovenia, May 31 -
               June 4, 2015. Proceedings},
  pages     = {353--368},
  year      = {2015},
  crossref  = {DBLP:conf/esws/2015},
  url       = {https://doi.org/10.1007/978-3-319-18818-8_22},
  doi       = {10.1007/978-3-319-18818-8_22},
  timestamp = {Fri, 02 Jun 2017 20:49:47 +0200},
  biburl    = {http://dblp.org/rec/bib/conf/esws/UsbeckNBU15},
  bibsource = {dblp computer science bibliography, http://dblp.org}
}

```
