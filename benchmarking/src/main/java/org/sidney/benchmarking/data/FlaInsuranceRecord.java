/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.benchmarking.data;

import com.univocity.parsers.annotations.Parsed;
import org.sidney.core.annotations.Encode;
import org.sidney.core.encoding.Encoding;

public class FlaInsuranceRecord {
    //policyID,statecode,county,eq_site_limit,hu_site_limit,fl_site_limit,fr_site_limit,tiv_2011,tiv_2012,eq_site_deductible,hu_site_deductible,
    //fl_site_deductible,fr_site_deductible,point_latitude,point_longitude,line,construction,point_granularity
    @Parsed(field = "policyID", defaultNullRead = "0")
    @Encode(Encoding.DELTABITPACKINGHYBRID)
    private int policyId;
    @Parsed(field = "statecode", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private String stateCode;
    @Parsed(field = "county", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private String county;
    @Parsed(field = "eq_site_limit", defaultNullRead = "0")
    @Encode(Encoding.PLAIN)
    private float eqSiteLimit;
    @Encode(Encoding.PLAIN)
    @Parsed(field = "hu_site_limit", defaultNullRead = "0")
    private float huSiteLimit;
    @Encode(Encoding.PLAIN)
    @Parsed(field = "fl_site_limit", defaultNullRead = "0")
    private float flSiteLimit;
    @Encode(Encoding.RLE)
    @Parsed(field = "fr_site_limit", defaultNullRead = "0")
    private float frSiteLimit;
    @Encode(Encoding.RLE)
    @Parsed(field = "tiv_2011", defaultNullRead = "0")
    private float tiv2011;
    @Encode(Encoding.RLE)
    @Parsed(field = "tiv_2012", defaultNullRead = "0")
    private float tiv2012;
    @Encode(Encoding.RLE)
    @Parsed(field = "eq_site_deductible", defaultNullRead = "0")
    private float eqSiteDeductible;
    @Encode(Encoding.RLE)
    @Parsed(field = "hu_site_deductible", defaultNullRead = "0")
    private float huSiteDeductible;
    @Encode(Encoding.RLE)
    @Parsed(field = "fl_site_deductible", defaultNullRead = "0")
    private float flSiteDeductible;
    @Encode(Encoding.RLE)
    @Parsed(field = "fr_site_deductible", defaultNullRead = "0")
    private float frSiteDeductible;
    @Encode(Encoding.RLE)
    @Parsed(field = "point_latitude", defaultNullRead = "0")
    private float pointLatitude;
    @Parsed(field = "point_longitude", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private float pointLongitude;
    @Parsed(field = "line", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private String line;
    @Parsed(field = "construction", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private String construction;
    @Parsed(field = "point_granularity", defaultNullRead = "0")
    @Encode(Encoding.RLE)
    private int pointGranularity;

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public float getEqSiteLimit() {
        return eqSiteLimit;
    }

    public void setEqSiteLimit(float eqSiteLimit) {
        this.eqSiteLimit = eqSiteLimit;
    }

    public float getHuSiteLimit() {
        return huSiteLimit;
    }

    public void setHuSiteLimit(float huSiteLimit) {
        this.huSiteLimit = huSiteLimit;
    }

    public float getFlSiteLimit() {
        return flSiteLimit;
    }

    public void setFlSiteLimit(float flSiteLimit) {
        this.flSiteLimit = flSiteLimit;
    }

    public float getFrSiteLimit() {
        return frSiteLimit;
    }

    public void setFrSiteLimit(float frSiteLimit) {
        this.frSiteLimit = frSiteLimit;
    }

    public float getTiv2011() {
        return tiv2011;
    }

    public void setTiv2011(float tiv2011) {
        this.tiv2011 = tiv2011;
    }

    public float getTiv2012() {
        return tiv2012;
    }

    public void setTiv2012(float tiv2012) {
        this.tiv2012 = tiv2012;
    }

    public float getEqSiteDeductible() {
        return eqSiteDeductible;
    }

    public void setEqSiteDeductible(float eqSiteDeductible) {
        this.eqSiteDeductible = eqSiteDeductible;
    }

    public float getHuSiteDeductible() {
        return huSiteDeductible;
    }

    public void setHuSiteDeductible(float huSiteDeductible) {
        this.huSiteDeductible = huSiteDeductible;
    }

    public float getFlSiteDeductible() {
        return flSiteDeductible;
    }

    public void setFlSiteDeductible(float flSiteDeductible) {
        this.flSiteDeductible = flSiteDeductible;
    }

    public float getFrSiteDeductible() {
        return frSiteDeductible;
    }

    public void setFrSiteDeductible(float frSiteDeductible) {
        this.frSiteDeductible = frSiteDeductible;
    }

    public float getPointLatitude() {
        return pointLatitude;
    }

    public void setPointLatitude(float pointLatitude) {
        this.pointLatitude = pointLatitude;
    }

    public float getPointLongitude() {
        return pointLongitude;
    }

    public void setPointLongitude(float pointLongitude) {
        this.pointLongitude = pointLongitude;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public int getPointGranularity() {
        return pointGranularity;
    }

    public void setPointGranularity(int pointGranularity) {
        this.pointGranularity = pointGranularity;
    }
}
