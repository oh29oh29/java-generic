# Java Generic

제네릭은 버그를 줄이고 유형에 대한 추가 추상화 계층을 추가하기 위해 Java 5 에 도입되었다.

## 제네릭의 필요성

```java
List list = new ArrayList();
list.add(1);
Integer i = list.iterator().next();
```

위와 같은 코드가 있다고 가정할 때, 컴파일러는 마지막 줄에서 에러를 발생시킨다.  
컴파일러에는 명시적 캐스팅이 필요하다.

```java
Integer i = (Integer) list.iterator().next();
```

정의된 리스트에는 모든 Object 가 포함될 수 있기 때문에 리스트의 반환 타입이 Integer 임을 보장할 수는 없다.  
타입을 볼 때, Object 임을 보장할 수만 있으므로 타입이 safe 한지 확인하려면 명시적 캐스팅이 필요하다.

그러나 이러한 캐스팅은 단점이 있다.  
우리는 이 리스트의 데이터 타입이 Integer 라는 것을 알고있다. 명시적 캐스팅은 코드를 복잡하게 만든다.  
개발자가 명시적 캐스팅에 실수를 하면 데이터 타입 관련해서 런타임 오류가 발생할 수 있다.

개발자가 특정 타입을 사용하려는 의도를 표현할 수 있고 컴파일러가 그러한 타입의 정확성을 보장할 수 있다면 이러한 단점을 극복할 수 있을 것이다.  
이것이 제네릭의 핵심 아이디어다.

```java
List<Integer> list = new ArrayList<>();
```

타입을 포함하는 다이아몬드 연산자 `<>` 를 추가하여 이 리스트의 특정 타입이 Integer 만으로 좁힌다.  
즉, 리스트 내에 고정될 타입을 지정한다. 컴파일러는 컴파일 타임에 타입을 적용할 수 있다.

작은 애플리케이션에서 이것은 사소한 추가처럼 보일 수 있지만 큰 애플리케이션에서는 상당한 견고성을 추가하고 코드를 더 쉽게 읽을 수 있도록 한다. 

## 제네릭 메서드

제네릭 메서드는 단일 메서드 선언으로 작성되고 다른 타입의 매개변수를 사용하여 호출할 수 있는 메서드이다.  
컴파일러는 사용되는 타입의 정확성을 보장한다. 다음은 제네릭 메서드의 몇 가지 속성이다.  
- 제네릭 메서드에는 메서드 선언의 반환 타입 앞에 타입 매개변수가 있다.
- 타입 매개변수는 제한될 수 있다.
- 제네릭 메서드는 메서드 시그니처에서 콤마로 구분된 다른 타입 매개변수를 가질 수 있다.
- 제네릭 메서드의 본문은 일반 메서드와 같다.

```java
public <T> List<T> fromArrayToList(T[] a) {   
    return Arrays.stream(a).collect(Collectors.toList());
}
```

메서드 시그니처의 <T> 는 메서드가 제네릭 타입 T 를 처리할 것임을 의미한다.  
메서드가 void 를 반환하더라도 필요하다.  

위에서 언급했듯이 메서드는 하나 이상의 제네릭 타입을 처리할 수 있다.  
이 경우 모든 제네릭 타입이 메서드 시그니처에 추가되어야 한다.  
예를 들어 위 메서드를 수정하여 타입 T 및 G 타입을 처리하기 위해 방법을 수정하면 아래와 같다.  

```java
public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
    return Arrays.stream(a)
      .map(mapperFunction)
      .collect(Collectors.toList());
}
```

T 타입의 요소가 있는 배열을 G 타입의 요소가 있는 리스트로 변환하는 함수를 전달한다.  

오라클 권장 사항은 제네릭 타입을 나타내기 위해 대문자를 사용하고 타입을 나타내기 위해 설명적인 문자를 선택하는 것이다.  
예를 들어 Java Collections 에서 T 는 타입, K 는 키, V 는 값을 의미한다.

#### Type Parameter Naming Conventions

- E: Element (used extensively by the Java Collections Framework)
- K: Key
- N: Number
- T: Type
- V: Value
- S,U,V etc.: 2nd, 3rd, 4th types

### Bounded Generics

앞서 언급했듯이 타입 매개변수는 제한될 수 있다.  
Bounded 는 '제한됨' 을 의미하며 메서드에서 허용할 수 있는 타입을 제한할 수 있다.  

예를 들어, 메서드가 타입 및 모든 하위 클래스(하한) 또는 모든 상위 클래스(상한)를 허용하도록 지정할 수 있다. 
상한 타입을 선언하기 위해 사용할 상한이 뒤따르는 타입 뒤에 키워드 extends 를 사용한다.

```java
public <T extends Number> List<T> fromArrayToList(T[] a) {
    ...
}
```

여기서 extends 키워드는 타입 T 가 클래스의 경우 상한을 상속받거나 인터페이스의 경우 상한을 구현한다는 의미로 사용된다.  

#### Multiple Bounds

타입은 다음과 같이 여러 상한을 가질 수 있다.  

```java
<T extends Number & Comparable>
```

T 에게 상속하는 타입 중 하나가 클래스인 경우 목록에서 첫 번째에 넣어야 한다. 그렇지 않으면 컴파일 타임 오류가 발생한다.

## Wildcards

와일드카드는 Java 에서 물음표로 표시된다. 알 수 없는 타입을 참조하는데 사용된다.  
와일드카드는 제네릭을 사용할 때 특히 유용하며 매개변수 타입으로 사용할 수 있지만 먼조 고려해야할 중요한 사항이 있다.

Object 는 모든 Java 클래스의 상위 클래스이지만 Object 컬렉션은 어느 컬렉션의 상위 클래스가 아니다.  

예를 들어, List<Object> 는 List<String> 의 상위 클래스가 아니므로 List<Object> 타입의 변수를 List<String> 타입의 변수에 할당하면 컴파일 오류가 발생한다.  
이는 동일한 컬렉션에 다른 타입을 추가할 때 발생할 수 있는 충돌을 방지하기 위한 것이다.
동일한 규칙은 타입 및 해당 하위 타입 컬렉션에 적용된다.  

```java
public static void paintAllBuildings(List<Building> buildings) {
    buildings.forEach(Building::paint);
}
```

예를 들어, House 와 같은 Building 의 하위 타입을 상상해본다면, 비록 House 가 Building 의 하위 타입일지라도 우리는 House 리스트를 paintAllBuildings() 메서드의 매개변수로 사용할 수 없다.  
House 타입 모든 하위 타입으로 paintAllBuildings() 메서드를 사용해야하는 경우, 다음과 같이 bounded 와일드카드로 작업을 수행할 수 있다.  

```java
public static void paintAllBuildings(List<? extends Building> buildings) {
    ...
}
```

이제 이 메서드는 Building 타입 및 모든 하위 타입으로 사용할 수 있다. 이를 상한 와일드카드라고 하며, 여기서 Building 타입이 상한이다.
와일드카드는 알 수 없는 타입이 지정된 타입의 상위 타입이어야 하는 하한으로 지정할 수도 있다.  
하한은 super 키워드를 사용하여 지정할 수 있으며, 예를 들어 <? super T> 는 T 를 상위 클래스로 하는 알 수 없는 타입을 의미한다.

<hr>

#### References

> 웹 문서
> - [baeldung | The Basics of Java Generics](https://www.baeldung.com/java-generics)