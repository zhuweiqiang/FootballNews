package me.coderblog.footballnews.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment工厂
 */
public class FragmentFactory {

    //Map用来存储生成的Fragment对象，以达到复用Fragment
    private static Map<Integer, Fragment> fragmentMap = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {

        //首先在Map查找有无对应的Fragment
        Fragment fragment = fragmentMap.get(position);

        if (fragment == null) {

            //如果Map中无对应的Fragment，则重新创建Fragment对象
            switch (position) {
                case 0:
                    fragment = new TopFragment();
                    break;
                case 1:
                    fragment = new CollectionFragment();
                    break;
                case 2:
                    fragment = new VideoFragment();
                    break;
                case 3:
                    fragment = new TransferFragment();
                    break;
                case 4:
                    fragment = new SubjectFragment();
                    break;
                case 5:
                    fragment = new DeepFragment();
                    break;
                case 6:
                    fragment = new ChinaFragment();
                    break;
                case 7:
                    fragment = new EnglandFragment();
                    break;
                case 8:
                    fragment = new SpainFragment();
                    break;
                case 9:
                    fragment = new GermanyFragment();
                    break;
                case 10:
                    fragment = new ItalyFragment();
                    break;
                case 11:
                    fragment = new GlobalFragment();
                    break;
            }

            //将生成的Fragment对象存入Map中以便复用
            fragmentMap.put(position, fragment);
        }

        //返回Fragment
        return fragment;
    }

}
