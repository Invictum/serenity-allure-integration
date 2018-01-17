[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6ef5bbc40e1f4afbbbd757bc8d2c98d7)](https://www.codacy.com/app/zim182/serenity-allure-integration?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Invictum/serenity-allure-integration&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Invictum/serenity-allure-integration.svg?branch=develop)](https://travis-ci.org/Invictum/serenity-allure-integration)

Serenity integration with Allure Reporting
==========================================

Module allows to produce [Allure](http://allure.qatools.ru) test reports.

Setup
-------------
To add support of Serenity with Allure integration simply add dependencies to your project
```
<dependency>
   <groupId>com.github.invictum</groupId>
   <artifactId>serenity-allure-integration</artifactId>
   <version>1.0.0</version>
</dependency>
```

From this point setup of integration is ready. Execute your tests normally and allure source files will appear in accordance to configuration.
To generate Allure HTML report from source files, refer to [Allure Reporting](https://docs.qameta.io/allure/#_reporting) section.

Allure version notes
--------------------

There are two versions of Allure 1.x and 2.x.

Current integration uses 2.x version. Allure2 is still in beta phase, so use integration with caution.


Versioning
----------
Report Portal integration uses 3 digit version format - x.y.z

**z** - regular release increment version. Includes bugfix and extending with minor features. Also includes Serenity and Allure modules versions update. Backward compatibility is guaranteed.

**y** - minor version update. Includes major Serenity and Allure core modules update. Backward compatibility for Serenity and Allure doesn't guaranteed.

**x** - major version update. Dramatically changed integration architecture. Backward compatibility doesn't guaranteed. Actually increment of major version is not expected at all
