package com.inmobi.grill.storage.db;

import static org.apache.hadoop.hive.serde.serdeConstants.LIST_COLUMNS;
import static org.apache.hadoop.hive.serde.serdeConstants.LIST_COLUMN_TYPES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.NotImplementedException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.Writable;

public class DBSerde extends AbstractSerDe {

  private ObjectInspector cachedObjectInspector;

  @Override
  public Object deserialize(Writable arg0) throws SerDeException {
    throw new NotImplementedException();
  }

  @Override
  public ObjectInspector getObjectInspector() throws SerDeException {
    return cachedObjectInspector;
  }

  @Override
  public SerDeStats getSerDeStats() {
    return null;
  }

  @Override
  public void initialize(Configuration conf, Properties tbl)
      throws SerDeException {
    String columnNameProperty = tbl.getProperty(LIST_COLUMNS);
    String columnTypeProperty = tbl.getProperty(LIST_COLUMN_TYPES);
    List<String> columnNames = Arrays.asList(columnNameProperty.split(","));
    List<TypeInfo> columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(
        columnTypeProperty);

    List<ObjectInspector> columnObjectInspectors = 
        new ArrayList<ObjectInspector>(columnNames.size());
    ObjectInspector colObjectInspector;
    for (int col = 0; col < columnNames.size(); col++) {
      colObjectInspector = TypeInfoUtils
          .getStandardJavaObjectInspectorFromTypeInfo(columnTypes.get(col));
      columnObjectInspectors.add(colObjectInspector);
    }

    cachedObjectInspector = ObjectInspectorFactory
        .getColumnarStructObjectInspector(columnNames, columnObjectInspectors);
  }

  @Override
  public Class<? extends Writable> getSerializedClass() {
    return null;
  }

  @Override
  public Writable serialize(Object arg0, ObjectInspector arg1)
      throws SerDeException {
    throw new NotImplementedException();
  }

}