package com.foxy_corporation.exchangerates;

import com.foxy_corporation.exchangerates.presenter.currencies.CurrenciesPresenterImp;
import com.foxy_corporation.exchangerates.view.currencies.CurrenciesActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Mockito.class)
public class CurrenciesPresenterImpTest {
    @Mock
    CurrenciesActivity view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        CurrenciesPresenterImp mCurrenciesPresenterImp = new CurrenciesPresenterImp();
        mCurrenciesPresenterImp.onAttachView(view);

        when(view.onStart()).thenReturn(new ArrayList<String>());
    }

    @Test
    public void testDisplayCalled() {
        verify(view).onStart();
    }

}