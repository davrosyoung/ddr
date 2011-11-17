package au.com.polly.ddr;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 17/11/11
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class GasWell
{
private String name;

public GasWell(String name)
{
    setName( name );
}

public String getName()
{
    return name;
}

public void setName(String name)
{
    this.name = name;
}
}
