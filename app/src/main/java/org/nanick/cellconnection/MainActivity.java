package org.nanick.cellconnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.lang.reflect.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {
    private DiCb telephonyCallback;
    public File csv;
    public FileWriter csv_writer;
    public Bands[] bands;
    public Nr5GBands[] nr5gbands;
    public String lte_string = "[{\"Downlink\":[2110.0,2140.0,2170.0],\"DLEARFCN\":[0.0,300.0,599.0],\"Uplink\":[1920.0,1950.0,1980.0],\"ULEARFCN\":[18000.0,18300.0,18599.0],\"Band\":1,\"Name\":\"2100\",\"Mode\":\"FDD\",\"Bandwidth\":60.0,\"DuplexSpacing\":190,\"Geographical\":\"Global\",\"GSM3GPP\":8},{\"Downlink\":[1930.0,1960.0,1990.0],\"DLEARFCN\":[600.0,900.0,1199.0],\"Uplink\":[1850.0,1880.0,1910.0],\"ULEARFCN\":[18600.0,18900.0,19199.0],\"Band\":2,\"Name\":\"1900 PCS\",\"Mode\":\"FDD\",\"Bandwidth\":60.0,\"DuplexSpacing\":80,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[1805.0,1842.5,1880.0],\"DLEARFCN\":[1200.0,1575.0,1949.0],\"Uplink\":[1710.0,1747.5,1785.0],\"ULEARFCN\":[19200.0,19575.0,19949.0],\"Band\":3,\"Name\":\"1800+\",\"Mode\":\"FDD\",\"Bandwidth\":75.0,\"DuplexSpacing\":95,\"Geographical\":\"Global\",\"GSM3GPP\":8},{\"Downlink\":[2110.0,2132.5,2155.0],\"DLEARFCN\":[1950.0,2175.0,2399.0],\"Uplink\":[1710.0,1732.5,1755.0],\"ULEARFCN\":[19950.0,20175.0,20399.0],\"Band\":4,\"Name\":\"AWS-1\",\"Mode\":\"FDD\",\"Bandwidth\":45.0,\"DuplexSpacing\":400,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[869.0,881.5,894.0],\"DLEARFCN\":[2400.0,2525.0,2649.0],\"Uplink\":[824.0,836.5,849.0],\"ULEARFCN\":[20400.0,20525.0,20649.0],\"Band\":5,\"Name\":\"850\",\"Mode\":\"FDD\",\"Bandwidth\":25.0,\"DuplexSpacing\":45,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[2620.0,2655.0,2690.0],\"DLEARFCN\":[2750.0,3100.0,3449.0],\"Uplink\":[2500.0,2535.0,2570.0],\"ULEARFCN\":[20750.0,21100.0,21449.0],\"Band\":7,\"Name\":\"2600\",\"Mode\":\"FDD\",\"Bandwidth\":70.0,\"DuplexSpacing\":120,\"Geographical\":\"EMEA\",\"GSM3GPP\":8},{\"Downlink\":[925.0,942.5,960.0],\"DLEARFCN\":[3450.0,3625.0,3799.0],\"Uplink\":[880.0,897.5,915.0],\"ULEARFCN\":[21450.0,21625.0,21799.0],\"Band\":8,\"Name\":\"900 GSM\",\"Mode\":\"FDD\",\"Bandwidth\":35.0,\"DuplexSpacing\":45,\"Geographical\":\"Global\",\"GSM3GPP\":8},{\"Downlink\":[1844.9,1862.5,1879.9],\"DLEARFCN\":[3800.0,3975.0,4149.0],\"Uplink\":[1749.9,1767.5,1784.9],\"ULEARFCN\":[21800.0,21975.0,22149.0],\"Band\":9,\"Name\":\"1800\",\"Mode\":\"FDD\",\"Bandwidth\":35.0,\"DuplexSpacing\":95,\"Geographical\":\"APAC\",\"GSM3GPP\":8},{\"Downlink\":[2110.0,2140.0,2170.0],\"DLEARFCN\":[4150.0,4450.0,4749.0],\"Uplink\":[1710.0,1740.0,1770.0],\"ULEARFCN\":[22150.0,22450.0,22749.0],\"Band\":10,\"Name\":\"AWS-3\",\"Mode\":\"FDD\",\"Bandwidth\":60.0,\"DuplexSpacing\":400,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[1475.9,1486.0,1495.9],\"DLEARFCN\":[4750.0,4850.0,4949.0],\"Uplink\":[1427.9,1438.0,1447.9],\"ULEARFCN\":[22750.0,22850.0,22949.0],\"Band\":11,\"Name\":\"1500 Lower\",\"Mode\":\"FDD\",\"Bandwidth\":20.0,\"DuplexSpacing\":48,\"Geographical\":\"Japan\",\"GSM3GPP\":8},{\"Downlink\":[729.0,737.5,746.0],\"DLEARFCN\":[5010.0,5095.0,5179.0],\"Uplink\":[699.0,707.5,716.0],\"ULEARFCN\":[23010.0,23095.0,23179.0],\"Band\":12,\"Name\":\"700 a\",\"Mode\":\"FDD\",\"Bandwidth\":17.0,\"DuplexSpacing\":30,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[746.0,751.0,756.0],\"DLEARFCN\":[5180.0,5230.0,5279.0],\"Uplink\":[777.0,782.0,787.0],\"ULEARFCN\":[23180.0,23230.0,23279.0],\"Band\":13,\"Name\":\"700 c\",\"Mode\":\"FDD\",\"Bandwidth\":10.0,\"DuplexSpacing\":-31,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[758.0,763.0,768.0],\"DLEARFCN\":[5280.0,5330.0,5379.0],\"Uplink\":[788.0,793.0,798.0],\"ULEARFCN\":[23280.0,23330.0,23379.0],\"Band\":14,\"Name\":\"700 PS\",\"Mode\":\"FDD\",\"Bandwidth\":10.0,\"DuplexSpacing\":-30,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[734.0,740.0,746.0],\"DLEARFCN\":[5730.0,5790.0,5849.0],\"Uplink\":[704.0,710.0,716.0],\"ULEARFCN\":[23730.0,23790.0,23849.0],\"Band\":17,\"Name\":\"700 b\",\"Mode\":\"FDD\",\"Bandwidth\":12.0,\"DuplexSpacing\":30,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[860.0,867.5,875.0],\"DLEARFCN\":[5850.0,5925.0,5999.0],\"Uplink\":[815.0,822.5,830.0],\"ULEARFCN\":[23850.0,23925.0,23999.0],\"Band\":18,\"Name\":\"800 Lower\",\"Mode\":\"FDD\",\"Bandwidth\":15.0,\"DuplexSpacing\":45,\"Geographical\":\"Japan\",\"GSM3GPP\":9},{\"Downlink\":[875.0,882.5,890.0],\"DLEARFCN\":[6000.0,6075.0,6149.0],\"Uplink\":[830.0,837.5,845.0],\"ULEARFCN\":[24000.0,24075.0,24149.0],\"Band\":19,\"Name\":\"800 Upper\",\"Mode\":\"FDD\",\"Bandwidth\":15.0,\"DuplexSpacing\":45,\"Geographical\":\"Japan\",\"GSM3GPP\":9},{\"Downlink\":[791.0,806.0,821.0],\"DLEARFCN\":[6150.0,6300.0,6449.0],\"Uplink\":[832.0,847.0,862.0],\"ULEARFCN\":[24150.0,24300.0,24449.0],\"Band\":20,\"Name\":\"800 DD\",\"Mode\":\"FDD\",\"Bandwidth\":30.0,\"DuplexSpacing\":-41,\"Geographical\":\"EMEA\",\"GSM3GPP\":9},{\"Downlink\":[1495.9,1503.5,1510.9],\"DLEARFCN\":[6450.0,6525.0,6599.0],\"Uplink\":[1447.9,1455.5,1462.9],\"ULEARFCN\":[24450.0,24525.0,24599.0],\"Band\":21,\"Name\":\"1500 Upper\",\"Mode\":\"FDD\",\"Bandwidth\":15.0,\"DuplexSpacing\":48,\"Geographical\":\"Japan\",\"GSM3GPP\":9},{\"Downlink\":[3510.0,3550.0,3590.0],\"DLEARFCN\":[6600.0,7000.0,7399.0],\"Uplink\":[3410.0,3450.0,3490.0],\"ULEARFCN\":[24600.0,25000.0,25399.0],\"Band\":22,\"Name\":\"3500\",\"Mode\":\"FDD\",\"Bandwidth\":80.0,\"DuplexSpacing\":100,\"Geographical\":\"EMEA\",\"GSM3GPP\":10},{\"Downlink\":[1525.0,1542.0,1559.0],\"DLEARFCN\":[7700.0,7870.0,8039.0],\"Uplink\":[1626.5,1643.5,1660.5],\"ULEARFCN\":[25700.0,25870.0,26039.0],\"Band\":24,\"Name\":\"1600 L-band\",\"Mode\":\"FDD\",\"Bandwidth\":34.0,\"DuplexSpacing\":-102,\"Geographical\":\"NAR\",\"GSM3GPP\":10},{\"Downlink\":[1930.0,1962.5,1995.0],\"DLEARFCN\":[8040.0,8365.0,8689.0],\"Uplink\":[1850.0,1882.5,1915.0],\"ULEARFCN\":[26040.0,26365.0,26689.0],\"Band\":25,\"Name\":\"1900+\",\"Mode\":\"FDD\",\"Bandwidth\":65.0,\"DuplexSpacing\":80,\"Geographical\":\"NAR\",\"GSM3GPP\":10},{\"Downlink\":[859.0,876.5,894.0],\"DLEARFCN\":[8690.0,8865.0,9039.0],\"Uplink\":[814.0,831.5,849.0],\"ULEARFCN\":[26690.0,26865.0,27039.0],\"Band\":26,\"Name\":\"850+\",\"Mode\":\"FDD\",\"Bandwidth\":35.0,\"DuplexSpacing\":45,\"Geographical\":\"NAR\",\"GSM3GPP\":11},{\"Downlink\":[852.0,860.5,869.0],\"DLEARFCN\":[9040.0,9125.0,9209.0],\"Uplink\":[807.0,815.5,824.0],\"ULEARFCN\":[27040.0,27125.0,27209.0],\"Band\":27,\"Name\":\"800 SMR\",\"Mode\":\"FDD\",\"Bandwidth\":17.0,\"DuplexSpacing\":45,\"Geographical\":\"NAR\",\"GSM3GPP\":11},{\"Downlink\":[758.0,780.5,803.0],\"DLEARFCN\":[9210.0,9435.0,9659.0],\"Uplink\":[703.0,725.5,748.0],\"ULEARFCN\":[27210.0,27435.0,27659.0],\"Band\":28,\"Name\":\"700 APT\",\"Mode\":\"FDD\",\"Bandwidth\":45.0,\"DuplexSpacing\":55,\"Geographical\":\"APAC,EU\",\"GSM3GPP\":11},{\"Downlink\":[717.0,722.5,728.0],\"DLEARFCN\":[9660.0,9715.0,9769.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":29,\"Name\":\"700 d\",\"Mode\":\"SDL\",\"Bandwidth\":11.0,\"DuplexSpacing\":0,\"Geographical\":\"NAR\",\"GSM3GPP\":11},{\"Downlink\":[2350.0,2355.0,2360.0],\"DLEARFCN\":[9770.0,9820.0,9869.0],\"Uplink\":[2305.0,2310.0,2315.0],\"ULEARFCN\":[27660.0,27710.0,27759.0],\"Band\":30,\"Name\":\"2300 WCS\",\"Mode\":\"FDD\",\"Bandwidth\":10.0,\"DuplexSpacing\":45,\"Geographical\":\"NAR\",\"GSM3GPP\":12},{\"Downlink\":[462.5,465.0,467.5],\"DLEARFCN\":[9870.0,9895.0,9919.0],\"Uplink\":[452.5,455.0,457.5],\"ULEARFCN\":[27760.0,27785.0,27809.0],\"Band\":31,\"Name\":\"450\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":10,\"Geographical\":\"Global\",\"GSM3GPP\":12},{\"Downlink\":[1452.0,1474.0,1496.0],\"DLEARFCN\":[9920.0,10140.0,10359.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":32,\"Name\":\"1500 L-band\",\"Mode\":\"SDL\",\"Bandwidth\":44.0,\"DuplexSpacing\":0,\"Geographical\":\"EMEA\",\"GSM3GPP\":12},{\"Downlink\":[1900.0,1910.0,1920.0],\"DLEARFCN\":[36000.0,36100.0,36199.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":33,\"Name\":\"TD 1900\",\"Mode\":\"TDD\",\"Bandwidth\":20.0,\"DuplexSpacing\":0,\"Geographical\":\"EMEA\",\"GSM3GPP\":8},{\"Downlink\":[2010.0,2017.5,2025.0],\"DLEARFCN\":[36200.0,36275.0,36349.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":34,\"Name\":\"TD 2000\",\"Mode\":\"TDD\",\"Bandwidth\":15.0,\"DuplexSpacing\":0,\"Geographical\":\"EMEA\",\"GSM3GPP\":8},{\"Downlink\":[1850.0,1880.0,1910.0],\"DLEARFCN\":[36350.0,36650.0,36949.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":35,\"Name\":\"TD PCS Lower\",\"Mode\":\"TDD\",\"Bandwidth\":60.0,\"DuplexSpacing\":0,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[1930.0,1960.0,1990.0],\"DLEARFCN\":[36950.0,37250.0,37549.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":36,\"Name\":\"TD PCS Upper\",\"Mode\":\"TDD\",\"Bandwidth\":60.0,\"DuplexSpacing\":0,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[1910.0,1920.0,1930.0],\"DLEARFCN\":[37550.0,37650.0,37749.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":37,\"Name\":\"TD PCS Center gap\",\"Mode\":\"TDD\",\"Bandwidth\":20.0,\"DuplexSpacing\":0,\"Geographical\":\"NAR\",\"GSM3GPP\":8},{\"Downlink\":[2570.0,2595.0,2620.0],\"DLEARFCN\":[37750.0,38000.0,38249.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":38,\"Name\":\"TD 2600\",\"Mode\":\"TDD\",\"Bandwidth\":50.0,\"DuplexSpacing\":0,\"Geographical\":\"EMEA\",\"GSM3GPP\":8},{\"Downlink\":[1880.0,1900.0,1920.0],\"DLEARFCN\":[38250.0,38450.0,38649.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":39,\"Name\":\"TD 1900+\",\"Mode\":\"TDD\",\"Bandwidth\":40.0,\"DuplexSpacing\":0,\"Geographical\":\"China\",\"GSM3GPP\":8},{\"Downlink\":[2300.0,2350.0,2400.0],\"DLEARFCN\":[38650.0,39150.0,39649.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":40,\"Name\":\"TD 2300\",\"Mode\":\"TDD\",\"Bandwidth\":100.0,\"DuplexSpacing\":0,\"Geographical\":\"China\",\"GSM3GPP\":8},{\"Downlink\":[2496.0,2593.0,2690.0],\"DLEARFCN\":[39650.0,40620.0,41589.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":41,\"Name\":\"TD 2600+\",\"Mode\":\"TDD\",\"Bandwidth\":194.0,\"DuplexSpacing\":0,\"Geographical\":\"Global\",\"GSM3GPP\":10},{\"Downlink\":[3400.0,3500.0,3600.0],\"DLEARFCN\":[41590.0,42590.0,43589.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":42,\"Name\":\"TD 3500\",\"Mode\":\"TDD\",\"Bandwidth\":200.0,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":10},{\"Downlink\":[3600.0,3700.0,3800.0],\"DLEARFCN\":[43590.0,44590.0,45589.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":43,\"Name\":\"TD 3700\",\"Mode\":\"TDD\",\"Bandwidth\":200.0,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":10},{\"Downlink\":[703.0,753.0,803.0],\"DLEARFCN\":[45590.0,46090.0,46589.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":44,\"Name\":\"TD 700\",\"Mode\":\"TDD\",\"Bandwidth\":100.0,\"DuplexSpacing\":0,\"Geographical\":\"APAC\",\"GSM3GPP\":11},{\"Downlink\":[1447.0,1457.0,1467.0],\"DLEARFCN\":[46590.0,46690.0,46789.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":45,\"Name\":\"TD 1500\",\"Mode\":\"TDD\",\"Bandwidth\":20.0,\"DuplexSpacing\":0,\"Geographical\":\"China\",\"GSM3GPP\":13},{\"Downlink\":[5150.0,5537.5,5925.0],\"DLEARFCN\":[46790.0,50665.0,54539.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":46,\"Name\":\"TD Unlicensed\",\"Mode\":\"TDD\",\"Bandwidth\":775.0,\"DuplexSpacing\":0,\"Geographical\":\"Global\",\"GSM3GPP\":13},{\"Downlink\":[5855.0,5890.0,5925.0],\"DLEARFCN\":[54540.0,54890.0,55239.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":47,\"Name\":\"TD V2X\",\"Mode\":\"TDD\",\"Bandwidth\":70.0,\"DuplexSpacing\":0,\"Geographical\":\"Global\",\"GSM3GPP\":14},{\"Downlink\":[3550.0,3625.0,3700.0],\"DLEARFCN\":[55240.0,55990.0,56739.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":48,\"Name\":\"TD 3600\",\"Mode\":\"TDD\",\"Bandwidth\":150.0,\"DuplexSpacing\":0,\"Geographical\":\"Global\",\"GSM3GPP\":14},{\"Downlink\":[3550.0,3625.0,3700.0],\"DLEARFCN\":[56740.0,57490.0,58239.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":49,\"Name\":\"TD 3600r\",\"Mode\":\"TDD\",\"Bandwidth\":150.0,\"DuplexSpacing\":0,\"Geographical\":\"Global\",\"GSM3GPP\":15},{\"Downlink\":[1432.0,1474.5,1517.0],\"DLEARFCN\":[58240.0,58665.0,59089.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":50,\"Name\":\"TD 1500+\",\"Mode\":\"TDD\",\"Bandwidth\":85.0,\"DuplexSpacing\":0,\"Geographical\":\"EU\",\"GSM3GPP\":15},{\"Downlink\":[1427.0,1429.5,1432.0],\"DLEARFCN\":[59090.0,59115.0,59139.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":51,\"Name\":\"TD 1500-\",\"Mode\":\"TDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":0,\"Geographical\":\"EU\",\"GSM3GPP\":15},{\"Downlink\":[3300.0,3350.0,3400.0],\"DLEARFCN\":[59140.0,59640.0,60139.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":52,\"Name\":\"TD 3300\",\"Mode\":\"TDD\",\"Bandwidth\":100.0,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":15},{\"Downlink\":[2483.5,2489.5,2495.0],\"DLEARFCN\":[60140.0,60197.0,60254.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":53,\"Name\":\"TD 2500\",\"Mode\":\"TDD\",\"Bandwidth\":11.5,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":16},{\"Downlink\":[1670.0,1672.5,1675.0],\"DLEARFCN\":[60255.0,60280.0,60304.0],\"Uplink\":[0.0,0.0,0.0],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":54,\"Name\":\"TD 1700\",\"Mode\":\"TDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":18},{\"Downlink\":[2110.0,2155.0,2200.0],\"DLEARFCN\":[65536.0,65986.0,66435.0],\"Uplink\":[1920.0,1965.0,2010.0],\"ULEARFCN\":[131072.0,131522.0,131971.0],\"Band\":65,\"Name\":\"2100+\",\"Mode\":\"FDD\",\"Bandwidth\":90.0,\"DuplexSpacing\":190,\"Geographical\":\"Global\",\"GSM3GPP\":13},{\"Downlink\":[2110.0,2155.0,2200.0],\"DLEARFCN\":[66436.0,66886.0,67335.0],\"Uplink\":[1710.0,1745.0,1780.0],\"ULEARFCN\":[131972.0,132322.0,132671.0],\"Band\":66,\"Name\":\"AWS\",\"Mode\":\"FDD\",\"Bandwidth\":90.0,\"DuplexSpacing\":400,\"Geographical\":\"NAR\",\"GSM3GPP\":13},{\"Downlink\":[738.0,748.0,758.0],\"DLEARFCN\":[67336.0,67436.0,67535.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":67,\"Name\":\"700 EU\",\"Mode\":\"SDL\",\"Bandwidth\":20.0,\"DuplexSpacing\":0,\"Geographical\":\"EMEA\",\"GSM3GPP\":13},{\"Downlink\":[753.0,768.0,783.0],\"DLEARFCN\":[67536.0,67686.0,67835.0],\"Uplink\":[698.0,713.0,728.0],\"ULEARFCN\":[132672.0,132822.0,132971.0],\"Band\":68,\"Name\":\"700 ME\",\"Mode\":\"FDD\",\"Bandwidth\":30.0,\"DuplexSpacing\":55,\"Geographical\":\"EMEA\",\"GSM3GPP\":13},{\"Downlink\":[2570.0,2595.0,2620.0],\"DLEARFCN\":[67836.0,68086.0,68335.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":69,\"Name\":\"DL b38\",\"Mode\":\"SDL\",\"Bandwidth\":50.0,\"DuplexSpacing\":0,\"Geographical\":\"\",\"GSM3GPP\":14},{\"Downlink\":[1995.0,2007.5,2020.0],\"DLEARFCN\":[68336.0,68461.0,68585.0],\"Uplink\":[1695.0,1702.5,1710.0],\"ULEARFCN\":[132972.0,133047.0,133121.0],\"Band\":70,\"Name\":\"AWS-4\",\"Mode\":\"FDD\",\"Bandwidth\":25.0,\"DuplexSpacing\":300,\"Geographical\":\"NAR\",\"GSM3GPP\":14},{\"Downlink\":[617.0,634.5,652.0],\"DLEARFCN\":[68586.0,68761.0,68935.0],\"Uplink\":[663.0,680.5,698.0],\"ULEARFCN\":[133122.0,133297.0,133471.0],\"Band\":71,\"Name\":\"600\",\"Mode\":\"FDD\",\"Bandwidth\":35.0,\"DuplexSpacing\":-46,\"Geographical\":\"NAR\",\"GSM3GPP\":15},{\"Downlink\":[461.0,463.5,466.0],\"DLEARFCN\":[68936.0,68961.0,68985.0],\"Uplink\":[451.0,453.5,456.0],\"ULEARFCN\":[133472.0,133497.0,133521.0],\"Band\":72,\"Name\":\"450 PMR/PAMR\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":10,\"Geographical\":\"EMEA\",\"GSM3GPP\":15},{\"Downlink\":[460.0,462.5,465.0],\"DLEARFCN\":[68986.0,69011.0,69035.0],\"Uplink\":[450.0,452.5,455.0],\"ULEARFCN\":[133522.0,133547.0,133571.0],\"Band\":73,\"Name\":\"450 APAC\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":10,\"Geographical\":\"APAC\",\"GSM3GPP\":15},{\"Downlink\":[1475.0,1496.5,1518.0],\"DLEARFCN\":[69036.0,69251.0,69465.0],\"Uplink\":[1427.0,1448.5,1470.0],\"ULEARFCN\":[133572.0,133787.0,134001.0],\"Band\":74,\"Name\":\"L-band\",\"Mode\":\"FDD\",\"Bandwidth\":43.0,\"DuplexSpacing\":48,\"Geographical\":\"NAR\",\"GSM3GPP\":15},{\"Downlink\":[1432.0,1474.5,1517.0],\"DLEARFCN\":[69466.0,69891.0,70315.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":75,\"Name\":\"DL b50\",\"Mode\":\"SDL\",\"Bandwidth\":85.0,\"DuplexSpacing\":0,\"Geographical\":\"EU\",\"GSM3GPP\":15},{\"Downlink\":[1427.0,1429.5,1432.0],\"DLEARFCN\":[70316.0,70341.0,70365.0],\"Uplink\":[],\"ULEARFCN\":[0.0,0.0,0.0],\"Band\":76,\"Name\":\"DL b51\",\"Mode\":\"SDL\",\"Bandwidth\":5.0,\"DuplexSpacing\":0,\"Geographical\":\"EU\",\"GSM3GPP\":15},{\"Downlink\":[728.0,737.0,746.0],\"DLEARFCN\":[70366.0,70456.0,70545.0],\"Uplink\":[698.0,707.0,716.0],\"ULEARFCN\":[134002.0,134092.0,134181.0],\"Band\":85,\"Name\":\"700 a+\",\"Mode\":\"FDD\",\"Bandwidth\":18.0,\"DuplexSpacing\":30,\"Geographical\":\"NAR\",\"GSM3GPP\":15},{\"Downlink\":[420.0,422.5,425.0],\"DLEARFCN\":[70546.0,70571.0,70595.0],\"Uplink\":[410.0,412.5,415.0],\"ULEARFCN\":[134182.0,134207.0,134231.0],\"Band\":87,\"Name\":\"410\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":10,\"Geographical\":\"EMEA\",\"GSM3GPP\":16},{\"Downlink\":[422.0,424.5,427.0],\"DLEARFCN\":[70596.0,70621.0,70645.0],\"Uplink\":[412.0,414.5,417.0],\"ULEARFCN\":[134232.0,134257.0,134281.0],\"Band\":88,\"Name\":\"410+\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":10,\"Geographical\":\"EMEA\",\"GSM3GPP\":16},{\"Downlink\":[757.0,757.5,758.0],\"DLEARFCN\":[70646.0,70651.0,70655.0],\"Uplink\":[787.0,787.5,788.0],\"ULEARFCN\":[134282.0,134287.0,134291.0],\"Band\":103,\"Name\":\"NB-IoT\",\"Mode\":\"FDD\",\"Bandwidth\":1.0,\"DuplexSpacing\":-30,\"Geographical\":\"\",\"GSM3GPP\":18},{\"Downlink\":[935.0,937.5,940.0],\"DLEARFCN\":[70656.0,70681.0,70705.0],\"Uplink\":[896.0,898.5,901.0],\"ULEARFCN\":[134292.0,134317.0,134341.0],\"Band\":106,\"Name\":\"900\",\"Mode\":\"FDD\",\"Bandwidth\":5.0,\"DuplexSpacing\":39,\"Geographical\":\"\",\"GSM3GPP\":18}]";
    public String nr5g_string = "[{\"Downlink\":[2110.0,2170.0],\"Uplink\":[1920.0,1980.0],\"DLNRARFCN\":[422000.0,434000.0],\"UPNRARFCN\":[384000.0,396000.0],\"Band\":\"n1\",\"Name\":\"2100\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1930.0,1990.0],\"Uplink\":[1850.0,1910.0],\"DLNRARFCN\":[386000.0,398000.0],\"UPNRARFCN\":[370000.0,382000.0],\"Band\":\"n2\",\"Name\":\"1900 PCS\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1805.0,1880.0],\"Uplink\":[1710.0,1785.0],\"DLNRARFCN\":[361000.0,376000.0],\"UPNRARFCN\":[342000.0,357000.0],\"Band\":\"n3\",\"Name\":\"1800\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[869.0,894.0],\"Uplink\":[824.0,849.0],\"DLNRARFCN\":[173800.0,178800.0],\"UPNRARFCN\":[164800.0,169800.0],\"Band\":\"n5\",\"Name\":\"850\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[2620.0,2690.0],\"Uplink\":[2500.0,2570.0],\"DLNRARFCN\":[524000.0,538000.0],\"UPNRARFCN\":[500000.0,514000.0],\"Band\":\"n7\",\"Name\":\"2600\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[925.0,960.0],\"Uplink\":[880.0,915.0],\"DLNRARFCN\":[185000.0,192000.0],\"UPNRARFCN\":[176000.0,183000.0],\"Band\":\"n8\",\"Name\":\"900\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[729.0,746.0],\"Uplink\":[699.0,716.0],\"DLNRARFCN\":[145800.0,149200.0],\"UPNRARFCN\":[139800.0,143200.0],\"Band\":\"n12\",\"Name\":\"700a - Lower SMH blocks A/B/C\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[746.0,756.0],\"Uplink\":[777.0,787.0],\"DLNRARFCN\":[149200.0,151200.0],\"UPNRARFCN\":[155400.0,157400.0],\"Band\":\"n13\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[758.0,768.0],\"Uplink\":[788.0,798.0],\"DLNRARFCN\":[151600.0,153600.0],\"UPNRARFCN\":[157600.0,159600.0],\"Band\":\"n14\",\"Name\":\"Upper SMH\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[860.0,875.0],\"Uplink\":[815.0,830.0],\"DLNRARFCN\":[172000.0,175000.0],\"UPNRARFCN\":[163000.0,166000.0],\"Band\":\"n18\",\"Name\":\"Lower 800 (Japan)\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[791.0,821.0],\"Uplink\":[832.0,862.0],\"DLNRARFCN\":[158200.0,164200.0],\"UPNRARFCN\":[166400.0,172400.0],\"Band\":\"n20\",\"Name\":\"EU Digital Dividend\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1525.0,1559.0],\"Uplink\":[1626.5,1660.5],\"DLNRARFCN\":[305000.0,311800.0],\"UPNRARFCN\":[325300.0,332100.0],\"Band\":\"n24\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1930.0,1995.0],\"Uplink\":[1850.0,1915.0],\"DLNRARFCN\":[386000.0,399000.0],\"UPNRARFCN\":[370000.0,383000.0],\"Band\":\"n25\",\"Name\":\"Extended PCS blocks A-G (superset of band 2)\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[859.0,894.0],\"Uplink\":[814.0,849.0],\"DLNRARFCN\":[171800.0,178800.0],\"UPNRARFCN\":[162800.0,169800.0],\"Band\":\"n26\",\"Name\":\"Extended CLR\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[758.0,803.0],\"Uplink\":[703.0,748.0],\"DLNRARFCN\":[151600.0,160600.0],\"UPNRARFCN\":[140600.0,149600.0],\"Band\":\"n28\",\"Name\":\"700 APT\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[717.0,728.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[143400.0,145600.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n29\",\"Name\":\"DL 700 blocks D/E\",\"Mode\":\"SDL\",\"BandWidth\":0.0},{\"Downlink\":[2350.0,2360.0],\"Uplink\":[2305.0,2315.0],\"DLNRARFCN\":[470000.0,472000.0],\"UPNRARFCN\":[461000.0,463000.0],\"Band\":\"n30\",\"Name\":\"WCS blocks A/B\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[2010.0,2025.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[402000.0,405000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n34\",\"Name\":\"TD 2000\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[2570.0,2620.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[514000.0,524000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n38\",\"Name\":\"TD 2600\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[1880.0,1920.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[376000.0,384000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n39\",\"Name\":\"TD 1900+\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[2300.0,2400.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[460000.0,480000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n40\",\"Name\":\"TD 2300\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[2496.0,2690.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[499200.0,537999.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n41\",\"Name\":\"TD 2500\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[5150.0,5925.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[743333.0,795000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n46\",\"Name\":\"TD 5200\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[3550.0,3700.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[636667.0,646666.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n48\",\"Name\":\"CBRS\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[1432.0,1517.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[286400.0,303400.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n50\",\"Name\":\"L-Band\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[1427.0,1432.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[285400.0,286400.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n51\",\"Name\":\"TD 1500-\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[2483.5,2495.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[496700.0,499000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n53\",\"Name\":\"S Band\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[2110.0,2200.0],\"Uplink\":[1920.0,2010.0],\"DLNRARFCN\":[422000.0,440000.0],\"UPNRARFCN\":[384000.0,402000.0],\"Band\":\"n65\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[2110.0,2200.0],\"Uplink\":[1710.0,1780.0],\"DLNRARFCN\":[422000.0,440000.0],\"UPNRARFCN\":[342000.0,356000.0],\"Band\":\"n66\",\"Name\":\"AWS-3\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[738.0,758.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[147600.0,151600.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n67\",\"Name\":null,\"Mode\":\"SDL\",\"BandWidth\":0.0},{\"Downlink\":[1995.0,2020.0],\"Uplink\":[1695.0,1710.0],\"DLNRARFCN\":[399000.0,404000.0],\"UPNRARFCN\":[339000.0,342000.0],\"Band\":\"n70\",\"Name\":\"AWS-4\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[617.0,652.0],\"Uplink\":[663.0,698.0],\"DLNRARFCN\":[123400.0,130400.0],\"UPNRARFCN\":[132600.0,139600.0],\"Band\":\"n71\",\"Name\":\"600\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1475.0,1518.0],\"Uplink\":[1427.0,1470.0],\"DLNRARFCN\":[295000.0,303600.0],\"UPNRARFCN\":[285400.0,294000.0],\"Band\":\"n74\",\"Name\":\"Lower L-Band\",\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1432.0,1517.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[286400.0,303400.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n75\",\"Name\":\"DL 1500+\",\"Mode\":\"SDL\",\"BandWidth\":0.0},{\"Downlink\":[1427.0,1432.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[285400.0,286400.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n76\",\"Name\":\"DL 1500-\",\"Mode\":\"SDL\",\"BandWidth\":0.0},{\"Downlink\":[3300.0,4200.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[620000.0,680000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n77\",\"Name\":\"TD 3700 (C-Band)\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[3300.0,3800.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[620000.0,653333.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n78\",\"Name\":\"TD 3500\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[4400.0,5000.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[693334.0,733333.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n79\",\"Name\":\"TD 4500\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[1710.0,1785.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[342000.0,357000.0],\"Band\":\"n80\",\"Name\":\"UL 1800\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[880.0,915.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[176000.0,183000.0],\"Band\":\"n81\",\"Name\":\"UL 900\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[832.0,862.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[166400.0,172400.0],\"Band\":\"n82\",\"Name\":\"UL 800\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[703.0,748.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[140600.0,149600.0],\"Band\":\"n83\",\"Name\":\"UL 700\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[1920.0,1980.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[384000.0,396000.0],\"Band\":\"n84\",\"Name\":\"UL 2000\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[728.0,746.0],\"Uplink\":[698.0,716.0],\"DLNRARFCN\":[145600.0,149200.0],\"UPNRARFCN\":[139600.0,143200.0],\"Band\":\"n85\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[1710.0,1780.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[342000.0,356000.0],\"Band\":\"n86\",\"Name\":\"UL 1800-\",\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[824.0,849.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[164800.0,169800.0],\"Band\":\"n89\",\"Name\":null,\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[2496.0,2690.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[499200.0,537999.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n90\",\"Name\":\"MMDS\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[1427.0,1432.0],\"Uplink\":[832.0,862.0],\"DLNRARFCN\":[285400.0,286400.0],\"UPNRARFCN\":[166400.0,172400.0],\"Band\":\"n91\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1432.0,1517.0],\"Uplink\":[832.0,862.0],\"DLNRARFCN\":[286400.0,303400.0],\"UPNRARFCN\":[166400.0,172400.0],\"Band\":\"n92\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1427.0,1432.0],\"Uplink\":[880.0,915.0],\"DLNRARFCN\":[285400.0,286400.0],\"UPNRARFCN\":[176000.0,183000.0],\"Band\":\"n93\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1432.0,1517.0],\"Uplink\":[880.0,915.0],\"DLNRARFCN\":[286400.0,303400.0],\"UPNRARFCN\":[176000.0,183000.0],\"Band\":\"n94\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[2010.0,2025.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[402000.0,405000.0],\"Band\":\"n95\",\"Name\":null,\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[5925.0,7125.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[795000.0,875000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n96\",\"Name\":\"U-NII (License Assisted Access)\",\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[2300.0,2400.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[460000.0,480000.0],\"Band\":\"n97\",\"Name\":null,\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[1880.0,1920.0],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[376000.0,384000.0],\"Band\":\"n98\",\"Name\":null,\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[0.0,0.0],\"Uplink\":[1626.5,1660.5],\"DLNRARFCN\":[0.0,0.0],\"UPNRARFCN\":[325300.0,332100.0],\"Band\":\"n99\",\"Name\":null,\"Mode\":\"SUL\",\"BandWidth\":0.0},{\"Downlink\":[919.4,925.0],\"Uplink\":[874.4,880.0],\"DLNRARFCN\":[183880.0,185000.0],\"UPNRARFCN\":[174880.0,176000.0],\"Band\":\"n100\",\"Name\":null,\"Mode\":\"FDD\",\"BandWidth\":0.0},{\"Downlink\":[1900.0,1910.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[380000.0,382000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n101\",\"Name\":null,\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[5925.0,6425.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[795000.0,828333.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n102\",\"Name\":null,\"Mode\":\"TDD\",\"BandWidth\":0.0},{\"Downlink\":[6425.0,7125.0],\"Uplink\":[0.0,0.0],\"DLNRARFCN\":[828334.0,875000.0],\"UPNRARFCN\":[0.0,0.0],\"Band\":\"n104\",\"Name\":null,\"Mode\":\"TDD\",\"BandWidth\":0.0}]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        while(
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||ActivityCompat.checkSelfPermission(this, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != PackageManager.PERMISSION_GRANTED || (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        setContentView(R.layout.activity_main);
        this.locationListener = new LocationListenerImpl();
        CreateCSVFile();
        try {
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint("MissingPermission")
    public void start() throws IOException {
        MainActivity m = new MainActivity();
        this.bands = BandsFromJson(this.lte_string);
        this.nr5gbands = Nr5GBandsFromJson(this.nr5g_string);
        this.locationManager = (LocationManager) MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this.locationListener);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        this.telephonyManager = (TelephonyManager)MainActivity.this.getSystemService(MainActivity.this.TELEPHONY_SERVICE);
        this.telephonyCallback = new DiCb();
        this.telephonyManager.registerTelephonyCallback(this.executorist,this.telephonyCallback);
        Runtime.getRuntime().exec("su");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SpeedTestTask test = new SpeedTestTask();
                test.execute();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
    private Executor executorist = new Executor() {
        @Override
        public void execute(Runnable r) {
            r.run();
        }
    };
    public class LocationListenerImpl implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Location loc = location;
                try {
                    if(loc != null){
                        ((TextView)findViewById(R.id.latitude)).setEnabled(true);
                        ((TextView)findViewById(R.id.latitude)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.latitude)).setText(loc.getLatitude()+"");
                        ((TextView)findViewById(R.id.latitude_label)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.latitude_label)).setEnabled(true);

                        ((TextView)findViewById(R.id.longitude)).setEnabled(true);
                        ((TextView)findViewById(R.id.longitude)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.longitude)).setText(loc.getLongitude()+"");
                        ((TextView)findViewById(R.id.longitude_label)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.longitude_label)).setEnabled(true);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                Log.i("cellspeed", "Location is null.");
            }
        }
        @Override
        public void onProviderDisabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                gpsEnabled = false;
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                networkEnabled = false;
            }
        }
        @Override
        public void onProviderEnabled(String provider) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                gpsEnabled = true;
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                networkEnabled = true;
            }
        }
    }
    public Bands[] BandsFromJson(String jsonString){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(jsonString, Bands[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void CreateCSVFile(){
        File csvdir = MainActivity.this.getExternalFilesDir(null);
        this.csv = new File(csvdir,Integer.valueOf((int) (System.currentTimeMillis() / 1000))+".csv");
        try {
            if (this.csv.createNewFile()) {
                this.csv_writer = new FileWriter(this.csv);
                this.csv_writer.write("Rsrp,Speed,TimingAdvance,Plmn,Tac,CellId,eNodeB,Rat,Lat,Lng,EARFCN,Band,Spectrum,NRSpectrum,Bandwidth,Pci,Rsrq,IPAddress,NRPci,NRTac,NRARFCN,NR5GBandName,TimeStamp\n");
                this.csv_writer.close();
                Log.i("celltop", "File created.");
            } else {
                Log.i("celltop", "Failed to create file.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Nr5GBands[] Nr5GBandsFromJson(String jsonString){
        ObjectMapper om = new ObjectMapper();
        try {

            return om.readValue(jsonString, Nr5GBands[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public LocationListener locationListener;
    public TelephonyManager telephonyManager;
    public TelephonyDisplayInfo telephonyDisplayInfo;
    private LocationManager locationManager;
    public Boolean gpsEnabled = true;
    public Boolean networkEnabled = true;
    public static String phoneState;
    public class DiCb extends TelephonyCallback implements TelephonyCallback.DisplayInfoListener {
        public String GetNetworkOverride(int networkOverride){
            switch(networkOverride){
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NONE:
                    return "None";
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_LTE_CA:
                    return "LTE CA";
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_LTE_ADVANCED_PRO:
                    return "LTE Advanced Pro";
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA:
                    return "NR NSA";
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA_MMWAVE:
                    return "NR NSA mmWave";
                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_ADVANCED:
                    return "NR Advanced";
                default:
                    return "Unknown";
            }
        }
        @Override
        public void onDisplayInfoChanged(TelephonyDisplayInfo displayInfo) {
            if (displayInfo != null) {
                String networkType;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    MainActivity.phoneState = GetNetworkOverride(displayInfo.getOverrideNetworkType());
                }
            }
        }
    }

//    private static TelephonyDisplayInfo getTelephonyDisplayInfo(TelephonyManager telephonyManager) {
//        try {
//            // Get TelephonyDisplayInfo using reflection
//            Method getTelephonyDisplayInfoMethod = telephonyManager.getClass().getMethod("getTelephonyDisplayInfo");
//            return (TelephonyDisplayInfo) getTelephonyDisplayInfoMethod.invoke(telephonyManager);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public String GetNetworkOverride(){
//        try {
//
//            Constructor<?> constructor = Class.forName("android.telephony.TelephonyDisplayInfo").getConstructor(new Class[0]);
//            constructor.setAccessible(true);
//            Object telephonyDisplayInfo = constructor.newInstance(new Object[0]);
//            Method getOverrideNetworkTypeMethod = telephonyDisplayInfo.getClass().getMethod("getOverrideNetworkType");
//            int networkOverride = (int) getOverrideNetworkTypeMethod.invoke(telephonyDisplayInfo);
//            switch(networkOverride){
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NONE:
//                    return "None";
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_LTE_CA:
//                    return "LTE CA";
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_LTE_ADVANCED_PRO:
//                    return "LTE Advanced Pro";
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA:
//                    return "NR NSA";
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_NSA_MMWAVE:
//                    return "NR NSA mmWave";
//                case TelephonyDisplayInfo.OVERRIDE_NETWORK_TYPE_NR_ADVANCED:
//                    return "NR Advanced";
//                default:
//                    return "Unknown";
//            }
//        }
//        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
    public class PubIPTask extends AsyncTask<Void,Void,String> {
        public CellInfoObj cio;
        public PubIPTask(CellInfoObj cello){
            this.cio = cello;
        }
        String url = "https://beserver.nanick.org/ip.php";
        byte[] buffer = new byte[16];
        @Override
        protected String doInBackground(Void... params){
            try {
                return getPubIP();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String IPAddress){
            cio.IPAddress = IPAddress;
            writeTextViews(cio);
            writeCsvRow(cio);
        }
        public String getPubIP() throws IOException {
            byte[] buff = new byte[16];
            InputStream inputStream = ((HttpURLConnection) new URL("https://beserver.nanick.org/ip.php").openConnection()).getInputStream();
            inputStream.read(buff,0,buff.length);
            String ipaddress = new String(buff);
            return ipaddress;
        }

    }
    public class SpeedTestTask extends AsyncTask<Void, Void, Double> {
        String testUrl = "https://storage.googleapis.com/nlp-137cf635-6c92-49b5-b943-f5c8c75e686f/testing.bin";
        byte[] buffer = new byte[32]; //byte[] buffer = new byte[13107200];
        @Override
        protected Double doInBackground(Void... params) {
            try {
                return performSpeedTest();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Double mbps) {
            getCellDetails(mbps);
        }
        private Double performSpeedTest() throws IOException {
            Double mbps = 0.0;
            InputStream inputStream = ((HttpURLConnection) new URL(this.testUrl).openConnection()).getInputStream();
            int bytesRead;
            int totalBytesRead = 0;
            Double startTime = Double.valueOf(System.currentTimeMillis());
            while ((bytesRead = inputStream.read(this.buffer)) != -1) {
                totalBytesRead += bytesRead;
            }
            Double endTime = Double.valueOf(System.currentTimeMillis());
            Double totalSeconds = (endTime - startTime) / 1000;
            mbps = 100 / totalSeconds;
            inputStream.close();
            return mbps;
        }
    }
    public void writeCsvRow(CellInfoObj cio){
        StringBuilder csvstring = new StringBuilder();
        csvstring.append(cio.Rsrp+",");
        csvstring.append(new DecimalFormat("0.00").format(cio.Speed) + ",");
        csvstring.append(cio.TimingAdvance+",");
        csvstring.append(cio.Plmn+",");
        csvstring.append(cio.Tac+",");
        csvstring.append(cio.CellId+",");
        csvstring.append(cio.eNodeB+",");
        csvstring.append(cio.Rat+",");
        csvstring.append(cio.Lat+",");
        csvstring.append(cio.Lng+",");
        csvstring.append(cio.EARFCN+",");
        csvstring.append(cio.Band+",");
        csvstring.append(cio.Spectrum+",");
        csvstring.append(cio.NRSpectrum+",");
        csvstring.append(cio.Bandwidth+",");
        csvstring.append(cio.Pci+",");
        csvstring.append(cio.Rsrq+",");
        csvstring.append(cio.IPAddress+",");
        csvstring.append(cio.NRPci+",");
        csvstring.append(cio.NRTac+",");
        csvstring.append(cio.NRARFCN+",");
        csvstring.append(cio.NR5GBandName+",");
        csvstring.append(cio.TimeStamp+"\n");
        String csv_string = csvstring.toString();
        try {
            csv_writer = new FileWriter(csv,true);
            csv_writer.write(csv_string);
            csv_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeTextViews(CellInfoObj cio){
        ((TextView)findViewById(R.id.rsrp)).setEnabled(true);
        ((TextView)findViewById(R.id.rsrp)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rsrp)).setText(cio.Rsrp+" dBm");
        ((TextView)findViewById(R.id.rsrp_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rsrp_label)).setEnabled(true);
        ((TextView)findViewById(R.id.downloadspeed)).setEnabled(true);
        ((TextView)findViewById(R.id.downloadspeed)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.downloadspeed)).setText(new DecimalFormat("0.00").format(cio.Speed)+" Mbps");
        ((TextView)findViewById(R.id.downloadspeed_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.downloadspeed_label)).setEnabled(true);
        if(cio.TimingAdvance != Integer.MAX_VALUE){
            ((TextView)findViewById(R.id.ta)).setEnabled(true);
            ((TextView)findViewById(R.id.ta)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.ta)).setText(cio.TimingAdvance+"");
            ((TextView)findViewById(R.id.ta_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.ta_label)).setEnabled(true);
        }
        ((TextView)findViewById(R.id.plmn)).setEnabled(true);
        ((TextView)findViewById(R.id.plmn)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.plmn)).setText(cio.Plmn);
        ((TextView)findViewById(R.id.plmn_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.plmn_label)).setEnabled(true);
        ((TextView)findViewById(R.id.lac)).setEnabled(true);
        ((TextView)findViewById(R.id.lac)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lac)).setText(cio.Tac+"");
        ((TextView)findViewById(R.id.lac_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lac_label)).setEnabled(true);
        ((TextView)findViewById(R.id.cellid)).setEnabled(true);
        ((TextView)findViewById(R.id.cellid)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.cellid)).setText(cio.CellId+"");
        ((TextView)findViewById(R.id.cellid_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.cellid_label)).setEnabled(true);
        ((TextView)findViewById(R.id.enodeb)).setEnabled(true);
        ((TextView)findViewById(R.id.enodeb)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.enodeb)).setText(cio.eNodeB+"");
        ((TextView)findViewById(R.id.enodeb_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.enodeb_label)).setEnabled(true);
        ((TextView)findViewById(R.id.rat)).setEnabled(true);
        ((TextView)findViewById(R.id.rat)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rat)).setText(cio.Rat);
        ((TextView)findViewById(R.id.rat_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rat_label)).setEnabled(true);
        ((TextView)findViewById(R.id.latitude)).setEnabled(true);
        ((TextView)findViewById(R.id.latitude)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.latitude)).setText(cio.Lat+"");
        ((TextView)findViewById(R.id.latitude_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.latitude_label)).setEnabled(true);
        ((TextView)findViewById(R.id.longitude)).setEnabled(true);
        ((TextView)findViewById(R.id.longitude)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.longitude)).setText(cio.Lng+"");
        ((TextView)findViewById(R.id.longitude_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.longitude_label)).setEnabled(true);
        ((TextView)findViewById(R.id.channel)).setEnabled(true);
        ((TextView)findViewById(R.id.channel)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.channel)).setText(cio.EARFCN+"");
        ((TextView)findViewById(R.id.channel_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.channel_label)).setEnabled(true);
        if(cio.NRARFCN != 0){
            ((TextView)findViewById(R.id.nrarfcn)).setEnabled(true);
            ((TextView)findViewById(R.id.nrarfcn)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrarfcn)).setText(cio.NRARFCN+"");
            ((TextView)findViewById(R.id.nrarfcn_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrarfcn_label)).setEnabled(true);
        }
        ((TextView)findViewById(R.id.lte_band)).setEnabled(true);
        ((TextView)findViewById(R.id.lte_band)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lte_band)).setText(cio.Band+"");
        ((TextView)findViewById(R.id.lte_band_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.lte_band_label)).setEnabled(true);
        if(cio.NR5GBandName != null){
            byte[] byteArray = cio.NR5GBandName.getBytes();
            StringBuilder ssbb = new StringBuilder();
            for(int b = 0; b < byteArray.length; b++){
                ssbb.append(Integer.valueOf(byteArray[b]));
            }
            Log.i("CellTop",ssbb.toString());
            ((TextView)findViewById(R.id.nr5gband)).setEnabled(true);
            ((TextView)findViewById(R.id.nr5gband)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nr5gband)).setText(cio.NR5GBandName);
            ((TextView)findViewById(R.id.nr5gband_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nr5gband_label)).setEnabled(true);
        }
        ((TextView)findViewById(R.id.spectrum)).setEnabled(true);
        ((TextView)findViewById(R.id.spectrum)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.spectrum)).setText(cio.Spectrum);
        ((TextView)findViewById(R.id.spectrum_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.spectrum_label)).setEnabled(true);
        if(cio.NRSpectrum != null){
            ((TextView)findViewById(R.id.nrspectrum)).setEnabled(true);
            ((TextView)findViewById(R.id.nrspectrum)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrspectrum)).setText(cio.NRSpectrum);
            ((TextView)findViewById(R.id.nrspectrum_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrspectrum_label)).setEnabled(true);
        }
        if(cio.Bandwidth != 0){
            ((TextView)findViewById(R.id.bandwidth)).setEnabled(true);
            ((TextView)findViewById(R.id.bandwidth)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.bandwidth)).setText(cio.Bandwidth+" MHz");
            ((TextView)findViewById(R.id.bandwidth_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.bandwidth_label)).setEnabled(true);
        }
        ((TextView)findViewById(R.id.pci)).setEnabled(true);
        ((TextView)findViewById(R.id.pci)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.pci)).setText(cio.Pci+"");
        ((TextView)findViewById(R.id.pci_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.pci_label)).setEnabled(true);
        if(cio.NRPci != 0){
            ((TextView)findViewById(R.id.nrpci)).setEnabled(true);
            ((TextView)findViewById(R.id.nrpci)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrpci)).setText(cio.NRPci+"");
            ((TextView)findViewById(R.id.nrpci_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrpci_label)).setEnabled(true);
        }
        ((TextView)findViewById(R.id.rsrq)).setEnabled(true);
        ((TextView)findViewById(R.id.rsrq)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rsrq)).setText(cio.Rsrq+"");
        ((TextView)findViewById(R.id.rsrq_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.rsrq_label)).setEnabled(true);
        ((TextView)findViewById(R.id.ipaddress)).setEnabled(true);
        ((TextView)findViewById(R.id.ipaddress)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.ipaddress)).setText(cio.IPAddress);
        ((TextView)findViewById(R.id.ipaddress_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.ipaddress_label)).setEnabled(true);
        if(cio.NRTac != 0){
            ((TextView)findViewById(R.id.nrtac)).setEnabled(true);
            ((TextView)findViewById(R.id.nrtac)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrtac)).setText(cio.NRTac+"");
            ((TextView)findViewById(R.id.nrtac_label)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.nrtac_label)).setEnabled(true);
        }
    }
    public CellInfoObj cio = null;
    @SuppressLint("MissingPermission")
    public void getCellDetails(Double mbps){
        this.cio = new CellInfoObj();
        cio.TimeStamp = Long.valueOf((long)(System.currentTimeMillis() / 1000));
        cio.Speed = mbps;
        if(this.telephonyManager == null){
            this.telephonyManager = (TelephonyManager)MainActivity.this.getSystemService(MainActivity.this.TELEPHONY_SERVICE);
        }
        cio.Plmn = telephonyManager.getNetworkOperator();
        Location location = getLastKnownLocation();
        cio.Lat = location.getLatitude();
        cio.Lng = location.getLongitude();
        @SuppressLint("MissingPermission") List<CellInfo> allCells = telephonyManager.getAllCellInfo();
        @SuppressLint("MissingPermission") CellInfo cellInfo = allCells.get(0);
        if(cellInfo instanceof CellInfoLte){
            CellIdentityLte cellId = (CellIdentityLte)cellInfo.getCellIdentity();
            CellSignalStrengthLte signalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
            StringJoiner sj = new StringJoiner(", ");
            IntStream.of(cellId.getBands()).forEach(x -> sj.add(String.valueOf(x)));
            cio.CellId = cellId.getCi();
            cio.Tac = cellId.getTac();
            cio.EARFCN = cellId.getEarfcn();
            Boolean found = false;
            for(int a = 0; a < this.bands.length; a++){
                Bands b = this.bands[a];
                ArrayList<Double> earfcnlist = b.dLEARFCN;
                Collections.sort(earfcnlist);
                double n_low = earfcnlist.get(0);
                double n_high = earfcnlist.get((earfcnlist.toArray().length - 1));
                if(cio.EARFCN >= n_low & cio.EARFCN <= n_high){
                    cio.Band = b.band;
                    cio.Spectrum = b.name;
                    found = true;
                }
            }
            cio.Bandwidth = cellId.getBandwidth() / 1000;
            cio.Pci = cellId.getPci();
            cio.Rsrp = signalStrengthLte.getRsrp();
            cio.Rsrq = signalStrengthLte.getRsrq();
            //cio.Snr = signalStrengthLte.getRssnr();
            cio.eNodeB = Math.floorDiv((int)cio.CellId,256);
            cio.TimingAdvance = signalStrengthLte.getTimingAdvance();
        } else if (cellInfo instanceof CellInfoNr) {
            CellInfoNr cinr = (CellInfoNr)cellInfo;
            CellSignalStrengthNr signalStrengthNr = (CellSignalStrengthNr) ((CellInfoNr)cellInfo).getCellSignalStrength();
            CellIdentityNr cellIdNr = (CellIdentityNr) ((CellInfoNr)cellInfo).getCellIdentity();
            cio.NRARFCN = cellIdNr.getNrarfcn();
            cio.NRPci = cellIdNr.getPci();
            cio.NRTac = cellIdNr.getTac();
            Boolean found = false;
            ArrayList<Nr5GBands> five1 = new ArrayList<Nr5GBands>();
            for(int a = 0; a < this.nr5gbands.length; a++){
                Nr5GBands b = this.nr5gbands[a];
                ArrayList<Double> nrarfcnlist = b.dlNRARFCN;
                Collections.sort(nrarfcnlist);
                double n_low = nrarfcnlist.get(0);
                double n_high = nrarfcnlist.get((nrarfcnlist.toArray().length - 1));
                if(cio.NRARFCN >= n_low & cio.NRARFCN <= n_high){
                    five1.add(b);
                }
            }
            if(five1.stream().count() == 1){
                cio.Nr5GBand = five1.get(0);
                cio.NR5GBandName = five1.get(0).band;
                cio.NRSpectrum = five1.get(0).name;
            } else if(five1.stream().count() > 1){
                ArrayList<Nr5GBands> five2 = new ArrayList<Nr5GBands>();
                for(int a = 0; a < five1.stream().count(); a++){
                    Nr5GBands b = five1.get(a);
                    if(b.name != ""){
                        five2.add(b);
                    }
                }
                if(five2.stream().count() == 1){
                    cio.Nr5GBand = five2.get(0);
                    cio.NR5GBandName = five2.get(0).band;
                    cio.NRSpectrum = five2.get(0).name;
                } else if(five2.stream().count() > 1){
                    Hashtable<Double,String> width = new Hashtable<Double,String>();
                    for(int a = 0; a < five2.stream().count(); a++){
                        Nr5GBands b = five2.get(a);
                        ArrayList<Double> nrarfcnlist = b.dlNRARFCN;
                        Collections.sort(nrarfcnlist);
                        double n_low = nrarfcnlist.get(0);
                        double n_high = nrarfcnlist.get((nrarfcnlist.toArray().length - 1));
                        width.put((n_high - n_low),b.band);
                    }
                    ArrayList<Double> keys = new ArrayList<Double>();
                    Enumeration key = width.keys();
                    while (key.hasMoreElements())
                    {
                        Double d = Double.parseDouble(key.nextElement().toString());
                        keys.add(d);
                    }
                    Double max = Collections.max(keys);
                    String ba = width.get(max);
                    for(int a = 0; a < five2.stream().count(); a++){
                        Nr5GBands b = five2.get(a);
                        if(b.band == ba){
                            cio.Nr5GBand = b;
                            cio.NR5GBandName = b.band;
                            cio.NRSpectrum = b.name;
                        }
                    }

                }
            }
        }
        for(CellInfo cellInfos : allCells){
            if(cellInfos instanceof CellInfoNr){
                CellInfoNr cinr = (CellInfoNr)cellInfos;
                CellSignalStrengthNr signalStrengthNr = (CellSignalStrengthNr) ((CellInfoNr)cellInfos).getCellSignalStrength();
                CellIdentityNr cellIdNr = (CellIdentityNr) ((CellInfoNr)cellInfos).getCellIdentity();
                cio.NRARFCN = cellIdNr.getNrarfcn();
                cio.NRPci = cellIdNr.getPci();
                cio.NRTac = cellIdNr.getTac();
                Boolean found = false;
                ArrayList<Nr5GBands> five1 = new ArrayList<Nr5GBands>();
                for(int a = 0; a < this.nr5gbands.length; a++){
                    Nr5GBands b = this.nr5gbands[a];
                    ArrayList<Double> nrarfcnlist = b.dlNRARFCN;
                    Collections.sort(nrarfcnlist);
                    double n_low = nrarfcnlist.get(0);
                    double n_high = nrarfcnlist.get((nrarfcnlist.toArray().length - 1));
                    if(cio.NRARFCN >= n_low & cio.NRARFCN <= n_high){
                        five1.add(b);
                    }
                }
                if(five1.stream().count() == 1){
                    cio.Nr5GBand = five1.get(0);
                    cio.NR5GBandName = five1.get(0).band;
                    cio.NRSpectrum = five1.get(0).name;
                } else if(five1.stream().count() > 1){
                    ArrayList<Nr5GBands> five2 = new ArrayList<Nr5GBands>();
                    for(int a = 0; a < five1.stream().count(); a++){
                        Nr5GBands b = five1.get(a);
                        if(b.name != ""){
                            five2.add(b);
                        }
                    }
                    if(five2.stream().count() == 1){
                        cio.Nr5GBand = five2.get(0);
                        cio.NR5GBandName = five2.get(0).band;
                        cio.NRSpectrum = five2.get(0).name;
                    } else if(five2.stream().count() > 1){
                        Hashtable<Double,String> width = new Hashtable<Double,String>();
                        for(int a = 0; a < five2.stream().count(); a++){
                            Nr5GBands b = five2.get(a);
                            ArrayList<Double> nrarfcnlist = b.dlNRARFCN;
                            Collections.sort(nrarfcnlist);
                            double n_low = nrarfcnlist.get(0);
                            double n_high = nrarfcnlist.get((nrarfcnlist.toArray().length - 1));
                            width.put((n_high - n_low),b.band);
                        }
                        ArrayList<Double> keys = new ArrayList<Double>();
                        Enumeration key = width.keys();
                        while (key.hasMoreElements())
                        {
                            Double d = Double.parseDouble(key.nextElement().toString());
                            keys.add(d);
                        }
                        Double max = Collections.max(keys);
                        String ba = width.get(max);
                        for(int a = 0; a < five2.stream().count(); a++){
                            Nr5GBands b = five2.get(a);
                            if(b.band == ba){
                                cio.Nr5GBand = b;
                                cio.NR5GBandName = b.band;
                                cio.NRSpectrum = b.name;
                            }
                        }

                    }
                }
            }
        }
        if(MainActivity.phoneState != null && MainActivity.phoneState != "None"){
            cio.Rat = MainActivity.phoneState;
        } else {
            cio.Rat = parseNetworkType(this.telephonyManager.getDataNetworkType());
        }
        PubIPTask pit = new PubIPTask(cio);
        pit.execute();
    }
    public String parseNetworkType(int networkType){
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "IDEN";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO_A";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO_B";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "EHRPD";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPAP";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_NR:
                return "NR";
            default:
                return "Unknown";
        }
    }
    // @SuppressLint("MissingPermission")
    // private Location getLastKnownLocation(Context context) {
    //     Location location = null;
    //     try {
    //         LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    //         Method getLastKnownLocation = LocationManager.class.getMethod("getLastKnownLocation", String.class);
    //         Object locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
    //         location = (Location) getLastKnownLocation.invoke(locationManager, LocationManager.GPS_PROVIDER);
    //         if (location != null) {
    //             double latitude = location.getLatitude();
    //             double longitude = location.getLongitude();
    //             String loc = "{\"lat\": "+latitude+",\"lng\": "+longitude+"}";
    //             Log.i("CellTop",loc);
    //         }
    //     } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
    //         e.printStackTrace();
    //     }
    //     return location;
    // }
   @SuppressLint("MissingPermission")
   private Location getLastKnownLocation() {
       Location gpsLocation = null;
       Location networkLocation = null;
       if (gpsEnabled) {
           gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       }
       if (networkEnabled) {
           networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
       }
       if (gpsLocation == null && networkLocation == null) {
           return null;
       } else if (gpsLocation != null && networkLocation != null) {
           long gpsTime = gpsLocation.getTime();
           long networkTime = networkLocation.getTime();
           if (gpsTime > networkTime) {
               return gpsLocation;
           } else {
               return networkLocation;
           }
       } else if (gpsLocation != null) {
           return gpsLocation;
       } else {
           return networkLocation;
       }
   }
    public class CellInfoObj {
        public CellInfoObj(){
        }
        public int Rsrp;
        public double Speed;
        public int TimingAdvance;
        public String Plmn;
        public int Tac;
        public int CellId;
        public int eNodeB;
        public String Rat;
        public double Lat;
        public double Lng;
        public int EARFCN;
        public int Band;
        public String Spectrum;
        public String NRSpectrum;
        public int Bandwidth;
        public int Pci;
        public int Rsrq;
        public String IPAddress;
        public int NRPci;
        public int NRTac;
        public int NRARFCN;
        public Nr5GBands Nr5GBand;
        public String NR5GBandName;
        public long TimeStamp;
    }
    public static String toJSON(Object object) throws JSONException, IllegalAccessException {
        String str = "";
        Class c = object.getClass();
        JSONObject jsonObject = new JSONObject();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            String value = String.valueOf(field.get(object));
            jsonObject.put(name, value);
        }
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
    public static String toJSON(List list ) throws JSONException, IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        for (Object i : list) {
            String jstr = toJSON(i);
            JSONObject jsonObject = new JSONObject(jstr);
            jsonArray.put(jsonArray);
        }
        return jsonArray.toString();
    }
}
