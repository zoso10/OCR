Java OCR Framework
==================

An Optical Character Recognition Framework written purely in Java.


Installation
------------

Build the project and add the jar for the project along with all the jars in the `jar` directory to your compile-time libraries.

Usage
-----

There are 4 main parts to OCR:

1. Normalization
2. Segmentation
3. Feature Extraction
4. Classification

Feature Extraction and Classification are the only required parts. For Feature Extraction there are 5 algorithms at your disposal

* Horizontal Celled Projection
* Vertical Celled Projection
* Horizontal Projection Histogram
* Vertical Projection Histogram
* Local Line Fitting

This framework loosely uses a [Fluent Interface][1] Builder syntax.

Example:

    OCR ocr = OCRBuilder
                .create()
                .normalization(new Normalization())
                .segmentation(new Segmentation())
                .featureExtraction(
                    FeatureExtractionBuilder
                        .create()
                        .children(
                            new HorizontalCelledProjection(5),
                            new VerticalCelledProjection(5),
                            new HorizontalProjectionHistogram(),
                            new VerticalProjectionHistogram(),
                            new LocalLineFitting(49))
                        .build())
                .neuralNetwork(
                    NeuralNetworkBuilder
                        .create()
                        .fromFile("neural_network.eg")
                        .build())
                .build();


Contributing
------------

Want to help out? Feel free to share your ideas.

1. Fork it.
2. Create a branch (`git checkout -b my_fancy_feature`)
3. Commit your changes (`git commit -am "Added amazing feature"`)
4. Push to the branch (`git push origin my_fancy_feature`)
5. Open a [Pull Request][2]

References
----------

* Arora, Sandhya (2008). “Combining Multiple Feature Extraction Techniques for Handwritten Devnagari Character Recognition”, IEEE Region 10 Colloquium. pp. 342-348
* Haykin, Simon (1999). “Neural Networks A Comprehensive Foundation”, 2nd Edition. Pearson Education.
* Perez, Juan-Carlos ; Vidal, Enrique ; Sanchez, Lourdes (1994). “Simple and Effective Feature Extraction for Optical Character Recognition”, Selected Paper From the 5th Spanish Symposium on Pattern Recognition and Image Analysis.
* Zahid Hossain, M. ; Ashraful Amin, M. ; Yan, Hong (2012). “Rapid Feature Extraction for Optical Character Recognition”, IEEE Transactions on Pattern Analysis and Machine Intelligence, Vol. 24, No. 6. pp. 801-813


Thanks
------

Thanks to Heaton Research for providing an amazing Neural Network framework.
Also thanks to Apache Math Commons for doing all the math without the mess.

[1]: http://martinfowler.com/bliki/FluentInterface.html
[2]: http://github.com/zoso10/OCR/pulls