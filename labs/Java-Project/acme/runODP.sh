THE_CLASSPATH=./build/libs/acme-odp.jar

for i in `ls ./lib/*.jar`
do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done

#---------------------------#
# run the odp program program #
#---------------------------#
java -Xmx1024m -Xms128m -cp ".:${THE_CLASSPATH}" com.acme.odp.ODP $@
