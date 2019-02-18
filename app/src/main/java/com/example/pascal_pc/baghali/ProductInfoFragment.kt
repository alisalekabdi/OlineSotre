//package com.example.pascal_pc.baghali
//
//
//import android.content.Context
//import android.media.Image
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.RatingBar
//import android.widget.TextView
//
//
//class ProductInfoFragment : Fragment() {
//
//    val PRODUCT_ID_KEY:String="product_id"
//    lateinit var mImgRecyclerView:RecyclerView
//    lateinit var mTitleTv:TextView
//    lateinit var mColorTv:TextView
//    lateinit var mTotalSell:TextView
//    lateinit var mRatingCount:TextView
//    lateinit var mProductRatingBar: RatingBar
//    lateinit var mDescription:TextView
//
//    fun newInstance(productId:Int):ProductInfoFragment{
//
//        var args: Bundle = Bundle()
//        args.putInt(PRODUCT_ID_KEY,productId)
//        var fragment=ProductInfoFragment()
//        fragment.arguments=args
//        return fragment
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        val view:View=inflater.inflate(R.layout.fragment_product_info, container, false)
//        mImgRecyclerView=view.findViewById(R.id.img_view_recycler)
//        mTitleTv=view.findViewById(R.id.title_tv)
//        mColorTv=view.findViewById(R.id.color_tv)
//        mTotalSell=view.findViewById(R.id.total_sell_tv)
//        mRatingCount=view.findViewById(R.id.rating_count_tv)
//        mProductRatingBar=view.findViewById(R.id.product_ratingBar)
//        mDescription=view.findViewById(R.id.description_tv)
//
//
//
//
//
//
//
//        return view
//    }
//    class GalleryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//
//
//        fun bind(image: Image) {
//
//        }
//    }
//    class GalleryAdapter(var imageList:List<Image>): RecyclerView.Adapter<GalleryHolder>() {
//
//
//        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GalleryHolder {
//            var view:View=LayoutInflater.from(p0!!.context).inflate()
//           var galleryHolder:GalleryHolder()
//        }
//
//        override fun getItemCount(): Int {
//            return imageList.size
//        }
//
//        override fun onBindViewHolder(p0: GalleryHolder, p1: Int) {
//            var Image=imageList.get(p1)
//            p0.bind(Image)
//        }
//    }
//
//
//}
//
//
