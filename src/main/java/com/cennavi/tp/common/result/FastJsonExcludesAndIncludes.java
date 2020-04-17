package com.cennavi.tp.common.result;

import java.util.HashSet;
import java.util.Set;

/**
 * 返回json中属性去除和包含
 */
public class FastJsonExcludesAndIncludes implements java.io.Serializable {
    private Set<String> excludes = new HashSet<String>();
    private Set<String> includes = new HashSet<String>();

    public Set<String> getExcludes() {
        return excludes;
    }

    public void putExcludes(String... properties) {
        for (String property : properties) {
            excludes.add(property);
        }
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public void putIncludes(String... properties) {
        for (String property : properties) {
            includes.add(property);
        }
    }
}
