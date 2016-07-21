/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2011-2016 Broad Institute, Aiden Lab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package dumponly.basics;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LRUCache<K, V> {
    private AtomicInteger maxEntries;
    private Map<K, V> map;

    public LRUCache(int max) {
        this.maxEntries = new AtomicInteger(max);
    }

    private void createMap() {
        this.map = Collections.synchronizedMap(new LinkedHashMap<K, V>(16, 0.75F, true) {

            static final long serialVersionUID = 11289371L;

            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > LRUCache.this.maxEntries.get();
            }
        });
    }

    private Map<K, V> getMap() {
        if (this.map == null) {
            this.createMap();
        }

        return this.map;
    }

    public V put(K k, V v) {
        return this.getMap().put(k, v);
    }

    public V get(Object key) {
        return this.getMap().get(key);
    }

    public boolean containsKey(Object o) {
        return this.getMap().containsKey(o);
    }
}