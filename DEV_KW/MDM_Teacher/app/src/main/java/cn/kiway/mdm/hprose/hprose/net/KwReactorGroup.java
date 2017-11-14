/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * ReactorGroup.java                                      *
 *                                                        *
 * hprose ReactorGroup class for Java.                    *
 *                                                        *
 * LastModified: Apr 15, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/
package cn.kiway.mdm.hprose.hprose.net;

import java.io.IOException;

public class KwReactorGroup {
    private final KwReactor[] reactors;
    private int index;

    public KwReactorGroup(int count) throws IOException  {
        reactors = new KwReactor[count];
        for (int i = 0; i < count; ++i) {
            reactors[i] = new KwReactor();
        }
    }

    public void start() {
        int n = reactors.length;
        for (int i = 0; i < n; ++i) {
            reactors[i].start();
        }
    }

    public void register(KwConnection conn) {
        int n = reactors.length;
        index = (index + 1) % n;
        reactors[index].register(conn);
    }

    public void close() {
        for (int i = reactors.length - 1; i >= 0; --i) {
            reactors[i].close();
        }
    }
}
