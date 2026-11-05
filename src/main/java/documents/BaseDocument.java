package documents;

public class BaseDocument {
    private String biddingZoneDomain;
    private String periodStart;
    private String periodEnd;
    private String resolution;

    public String getBiddingZoneDomain() {
        return biddingZoneDomain;
    }

    public void setBiddingZoneDomain(String biddingZoneDomain) {
        this.biddingZoneDomain = biddingZoneDomain;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }
}