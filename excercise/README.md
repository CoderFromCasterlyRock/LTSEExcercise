
**Running Application**
```
git clone https://github.com/CoderFromCasterlyRock/LTSEExcercise.git
cd LTSEExcercise/excercise
mvn clean install -Dmaven.test.skip=true
java -jar target/excercise-1.0.0-jar-with-dependencies.jar
```

**Running Tests**
```
git clone https://github.com/CoderFromCasterlyRock/LTSEExcercise.git
cd LTSEExcercise/excercise 
mvn clean test
```


**Assumptions**
1. In the broker input file, Wells Fargo AdvisorsWaddell & Reed were listed as 1 broker. I separated them.
2. Used 3rd party gson lib for the extra credit part.
