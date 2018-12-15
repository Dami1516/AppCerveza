package dfuentes.appcerveza;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"Receta 1", "Receta 2"};
    private int count=1;
    private LinkedList<objetos.recetas> recetas;

    public ViewPagerAdapter(FragmentManager manager, int count, LinkedList<objetos.recetas> recetas) {
        super(manager);
        this.count=count;
        this.recetas=recetas;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeFragment.getInstance(position,recetas.get(position));
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count=count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}