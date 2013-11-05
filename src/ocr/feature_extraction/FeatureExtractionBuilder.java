package ocr.feature_extraction;

public class FeatureExtractionBuilder<B extends FeatureExtractionBuilder<B>> {

    private FeatureExtraction instance;
    
    public FeatureExtractionBuilder() {
        this(new FeatureExtraction());
    }
    
    protected FeatureExtractionBuilder(FeatureExtraction fe) {
        instance = fe;
    }
    
    public static FeatureExtractionBuilder<?> create() {
        
        return new FeatureExtractionBuilder();
    }
    
    public B children(FEMethod... m) {
        instance.addMethods(m);
        
        return (B)this;
    }
    
    public FeatureExtraction build() {
        
        return instance;
    }
}
