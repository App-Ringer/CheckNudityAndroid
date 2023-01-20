## CheckNudityAndroid

## Usage

This 'CheckNudity' returns you a float value from 0 to 1.
Value near 0 indicates provided image is SFW and value near 1 indicates provided image is NSFW.

Notation:
```
SFW = Safe for work
NSFW = Not Safe for work

```

Initialization:
```
call bellow method in the onCreate function of Application class

NudityModel.init(
            context = this,
            numThreads = 4,
            isOpenGPU = true
        )

```

Score:
```
nsfwScore = Not safe for work value
sfwScore = Safe for work value
loadTime = Time consumed to load the image
scanTime = Time consumed to scan the image
bitmap = Provided bitmap// (Note: this will be null in case of average score of a video)

```

If you have single bitmap - Pass bitmap to below mentioned method:
```
   NudityModel.checkNudity(bitmap) {score->

   }

```


If you have bitmap array - Pass array to below mentioned method:
```
   NudityModel.checkNudity(bitmaps, SecurityLevelEnum.LOW) { scorePerFrame, averageScore->

     }

```


If you have video in your local - Pass Video Uri to below mentioned method:
```
  NudityModel.checkNudity(context, uri, SecurityLevelEnum.LOW) { scorePerFrame, averageScore->

  }

```

## SecurityLevel

Total 3 security level High, Medium & Low.

| High | Medium | Low |
| --- | --- | --- |
| Frame captured with 1 Second interval | Frame captured with 2 Second interval | Frame captured with 4 Second interval |


## License

CheckNudity is released under the MIT license. [See LICENSE](http://www.opensource.org/licenses/MIT) for details.
