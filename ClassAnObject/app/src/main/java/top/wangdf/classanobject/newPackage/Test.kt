package top.wangdf.classanobject

/**
 * 型变 -- 本质上的就是类型转换。型变可以将接口、类泛化。
 * 型变的前提是确保泛型接口类型转换时安全的。所有Kotlin中有的泛型接口是可型变的，有的接口有是不可型变的。
 * Kotlin 中 List是可型变的，在Java中List是不可型变的。
 */
//List 泛型接口是可以型变
fun printlnElement(list: List<Any>) {
    list.forEach {
        println(it)
    }
}

fun mutableListPrintln(mutableList: MutableList<Any>) {
    mutableList.add(12.1)//向MutableList中插入一个浮点数是合法的
    mutableList.forEach {
        println(it)
    }
}

fun main() {
    val intList:List<Int> = listOf(1, 2, 3) // List<Int>可以类型转换List<Any>
    val stringList:List<String> = listOf("str1", "str2", "str3")//List<String> 可以类型转换为List<Any>
    printlnElement(intList)
    printlnElement(stringList)
    println("---------------------")

    val intMutableList: MutableList<Int> = mutableListOf(1, 2, 3)
    val stringMutableList: MutableList<String> = mutableListOf("str1", "str2", "str3")
    //printlnElement(mutableListPrintln(intMutableList))//MutableList<Int> 不可以型变为 MutableList<Any>
    //printlnElement(mutableListPrintln(stringList))//MutableList<String> 不可以型变为 MutableList<Any>

    /**
     * List泛型接口可以型变但是MutableList接口不可以型变。假设MutableList泛型接口可以型变， 那么在
     * mutableListPrintln 中插入一个浮点型的数据是合法的。因为参数mutableList类型是 MutableList<Any>
     * 那么函数调用之后就会出现 intMutableList/stringMutableList 中出现一个浮点类型的数。反过来，List中没有
     * 可以修改元素的方法，那么List 就可以进行类型转化，换句话说。List是可以型变，MutableList不可以型变。
     *
     * kotlin List 不可以型变的根源在于不可以进行元素的增加。这样保证List在任何的过程中不会出现错误类型的情况
     * Java List 中可以添加元素。Java List不可以型变
     *
     */

    /**
     * MutableList<E> : List<E> MutableList 泛型接口的定义
     * List<out E> : Collection<E> List 泛型接口的定义
     */

    /**
     * 子类型关系
     * List<String>、List<Int> 是 List<Any> 的子类型。List<Any> 是 List<String>、List<Int> 的超类型。
     * 所以List<String>、List<Int> 可以替换 List<Any> 。
     * MutableList<String>、MutableList<Int> 不是MutableList<Any>的子类型。MutableList<Any> 也不是
     * MutableList<String>、MutableList<Int> 的超类型。
     *
     * 现在注意下：String、Int  和 Any 之间的类型关系。可以发现String、Int 是 Any的子类型关系。List泛型接口
     * 保留了String、Int 、Any 的子类型关系。然后List<String> 、List<Int> 同时还是 List<Any> 的子类型。
     * 这种和泛型形参之间产生联系。称为 保留子类型关系。 也称为协变。
     */
}


