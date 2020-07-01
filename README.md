## レシピレコメンドアプリ

gRPC を使用したクライアントーサーバー アプリケーションのAndroidクライアントアプリです。

自宅にある食材や利用者の苦手な食べ物の情報を元にサーバーと通信し、オススメのレシピがレコメンドされます。

サーバーはクライアントから取得した食材情報を用いて、レシピサイトからオススメをレコメンドします。

## サービス概要
![スクリーンショット 2020-07-01 15 07 14](https://user-images.githubusercontent.com/57245344/86209697-189f0600-bbae-11ea-8a62-5de06eed9f7b.png)
## 開発背景

家にある食材で作れる料理を手軽にレコメンドしてくれるアプリが欲しいと思い、制作しました。

## 工夫した点

Android Jetpack コンポーネントをできるだけ利用して開発しました。

## ライブラリ
* Android Jetpack
  * Foundation
    * AppCompat
    * Android KTX
    * Multidex
    * Test
  * Architecture
    * Data Binding
    * Lifecycles
    * LiveData
    * Navigation
    * Room
    * ViewModel
  * UI
    * Fragment
    * ConstraintLayout
    * CardView
    * RecyclerView
* Third party
  * Kotlin Coroutines
  * gRPC
