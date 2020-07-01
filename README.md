## レシピレコメンドアプリ

gRPC を使用したクライアントーサーバー アプリケーションのAndroidクライアントアプリです。

自宅にある食材や利用者の苦手な食べ物の情報を元にサーバーと通信し、オススメのレシピがレコメンドされます。

サーバーはクライアントから取得した食材情報を用いて、レシピ系APIを使ってオススメレシピをレコメンドします。

## 開発背景

家にある食材で作れる料理を手軽にレコメンドしてくれるアプリが欲しいと思い、制作しました。

## 重視している点

シンプルなUIで使いやすさを重視しています。

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

## 使い方

家にある食材、調味料、苦手な食べ物を登録

↓

BottomNavigationの検索タブをタップ

↓

オススメレシピが取得される

↓

気に入ったレシピはお気に入りへ保存

## スクリーンショット

準備中

