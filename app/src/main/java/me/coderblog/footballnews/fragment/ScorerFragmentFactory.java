package me.coderblog.footballnews.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 射手榜Fragment工厂
 */
public class ScorerFragmentFactory {
    //Map用来存储生成的Fragment对象，以达到复用Fragment
    private static Map<Integer, Fragment> fragmentMap = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {

        //首先在Map查找有无对应的Fragment
        Fragment fragment = fragmentMap.get(position);

        if (fragment == null) {

            //如果Map中无对应的Fragment，则重新创建Fragment对象
            switch (position) {
                case 0:
                    fragment = new ChinaScorerFragment();
                    break;
                case 1:
                    fragment = new EnglandScorerFragment();
                    break;
                case 2:
                    fragment = new SpainScorerFragment();
                    break;
                case 3:
                    fragment = new GermanyScorerFragment();
                    break;
                case 4:
                    fragment = new ItalyScorerFragment();
                    break;
            }

            //将生成的Fragment对象存入Map中以便复用
            fragmentMap.put(position, fragment);
        }

        //返回Fragment
        return fragment;
    }
}
