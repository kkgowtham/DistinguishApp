package fictionstudios.com.distinguishapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDrawer();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new MainFragment()).commit();
    }

   private void setUpDrawer() {

        Drawable drawable;
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable=getDrawable(R.drawable.ic_like);
       }
       else {
            drawable= getResources().getDrawable(R.drawable.ic_like);
       }
       AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(drawable)
                )
                .build();
      /*  List<IDrawerItem> multiList=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            ExpandableDrawerItem muliLevel=new ExpandableDrawerItem().withName("MuliLeve").withSubItems(list);
            multiList.add(muliLevel);
        }
        ExpandableDrawerItem expandableDrawerItem=new ExpandableDrawerItem().withName("Category").withSubItems(multiList);

*/
      PrimaryDrawerItem item=new PrimaryDrawerItem().withTextColor(Color.BLUE).withName("Sign In");
               PrimaryDrawerItem item1=new PrimaryDrawerItem().withTextColor(Color.BLUE).withName("Add Post");

       Drawer drawer=new DrawerBuilder().withAccountHeader(headerResult).withActivity(this).withTranslucentStatusBar(false)
               .addDrawerItems(item,item1).build();
       drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
           @Override
           public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
               if (position==1)
               {
                   ProfileFragment fragment=new ProfileFragment();
                   getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
               }
               if (position==2)
               {
                   startActivity(new Intent(MainActivity.this,AddPostActivity.class));
               }
               return false;
           }
       });
    }

}
